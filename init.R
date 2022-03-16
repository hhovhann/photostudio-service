require(httr)
require(jsonlite)
require(readr)
library(Rook)

library(readr)
library(dplyr)
library(caret)
library(text2vec)
library(ngram)
library(tm)
library(glmnet)
library(janitor)
library(tidyr)
library(tidytext)
library(stringr)
library(parallel)
library(tibble)
library(ggplot2)
library(Cubist)
library(plumber)

#* Health check
#' @get /process/status
function() {
  list(status = "OK")
}
#* Post method
#' @post /process/<mode>/<size>
#' @serializer csv
function(req, mode, size, res) {
  file <- Rook::Multipart$parse(req)$req$tempfile
  data_select <- sub("\\..*", "", Rook::Multipart$parse(req)$req$filename)
  data_in <- utils::read.csv(file, fileEncoding = "UTF-8-BOM", sep = ",")
  result <- runScoring(data_select, mode, data_in, size)
  plumber::serializer_csv(result)

  now <- Sys.time()
  plumber::as_attachment(result, filename = base::paste(format(now, "%Y%m%d_%H%M%S_"), data_select, mode, "result.csv", sep = "_"))

}

runScoring <- function(data_select, model_select, data_in, size) {
  print("=============================================  START  =============================================")

  modes_list <- list('dtmLM'
    , 'dtmSVM'
    , 'dtm_Cubist'
    , 'dtm_rrf'
    , 'dtm_SVMRadial'
    , 'model.rf'
    , 'model.nnet'
    , 'dtm_GBM'
    , 'dtm_avNNet'
    , 'dtm_rrlda'
    , 'dtmQRF'
    , 'dtmQRNN'
    , 'glmnet'
    , 'glmnet_classifier_mae'
    , 'glmnet_classifier_mse')

  if (model_select %in% modes_list) {

    print("=============================================  MODEL CORRECTLY ASSIGNED  =============================================")

    smp_siz_control_in <- as.numeric(size)
    print("Assigned size:")
    print(smp_siz_control_in)

    essay_frame <- data_in %>% clean_names()
    if (data_select %in% c(
      "350not"
      , "EngEssayInputRubricsNoOmits"
      , "EngEssayInputTotalOnly"
      , "StratifiedSample350"
    )) {
      essay_frame <- essay_frame %>% rename(score = points)

    } else if (data_select == "AchievePrompt08") {

      essay_frame <- essay_frame %>% rename(examinee = test_code)

    }  else if (data_select == "EngEssayInputRubricsOmits") {

      essay_frame <- essay_frame %>% rename(
        score = total,
        examinee = code
      )
    }
    print("=============================================  INPUT ASSIGNED  =============================================")


    # < Text Cleaning > ####

    # Remove Stop-words
    # Remove Punctuation

    data(stop_words)

    essay_frame <- essay_frame %>%
      unnest_tokens(word, essay) %>%
      anti_join(stop_words, by = "word") %>%
      group_by(examinee) %>%
      mutate(essay = paste(word, collapse = ' ')) %>%
      select(-word) %>%
      distinct() %>%
      ungroup() %>%
      mutate(essay = str_replace_all(essay, "'", ""))


    # < Split into Training and Test sets > ####

    set.seed(123)

    smp_siz_control <- smp_siz_control_in
    smp_siz <- floor(smp_siz_control * nrow(essay_frame))
    train_ind <- sample(seq_len(nrow(essay_frame)), size = smp_siz)
    training_set <- essay_frame[train_ind,]
    test_set <- essay_frame[-train_ind,]

    # nrow(training_set)
    # nrow(test_set)

    # < Word count > ####

    word_count_vector_training <- as.matrix(sapply(training_set$essay, wordcount))
    word_count_vector_test <- as.matrix(sapply(test_set$essay, wordcount))

    colnames(word_count_vector_training, do.NULL = FALSE)
    colnames(word_count_vector_training) <- c("WordCount")
    colnames(word_count_vector_test, do.NULL = FALSE)
    colnames(word_count_vector_test) <- c("WordCount")


    # < AEM_train > ####

    prep_fun = tolower
    tok_fun = word_tokenizer

    AEM_train = itoken(training_set$essay,
                       preprocessor = prep_fun,
                       tokenizer = tok_fun,
                       ids = training_set$examinee,
                       progressbar = FALSE)

    vocab = create_vocabulary(AEM_train)


    # < DTM > ####

    #### Build the TrainingSet DTM

    vectorizer = vocab_vectorizer(vocab)
    dtmTrain = create_dtm(AEM_train, vectorizer)
    vocab_2 = create_vocabulary(AEM_train, ngram = c(1L, 2L))

    #### Prune the TrainingSet DTM

    doc_count_min_control <- 50
    pruned_vocab <- prune_vocabulary(vocab, doc_count_min = doc_count_min_control)
    pruned_vectorizer <- vocab_vectorizer(pruned_vocab)
    dtm_pruned_train <- create_dtm(AEM_train, pruned_vectorizer)


    #### Prepend the Word Count

    dtm_pruned_train <- cbind(
      word_count_vector_training
      , dtm_pruned_train
    )


    #### BiGram/Prune TrainingSet DTM

    doc_count_min_control <- 20
    pruned_vocab_2 <- prune_vocabulary(vocab_2, doc_count_min = doc_count_min_control)
    pruned_vectorizer_2 <- vocab_vectorizer(pruned_vocab_2)
    dtm_pruned_train_2 <- create_dtm(AEM_train, pruned_vectorizer_2)


    #### Prepend the Word Count

    dtm_pruned_train_2 <- cbind(
      word_count_vector_training
      , dtm_pruned_train_2
    )


    #### Build the PRUNED TestSet DTM

    AEM_Test = itoken(test_set$essay,
                      preprocessor = prep_fun,
                      tokenizer = tok_fun,
                      ids = test_set$examinee,
                      progressbar = FALSE)

    dtm_pruned_test = create_dtm(AEM_Test, pruned_vectorizer)


    #### Prepend the Word Count

    dtm_pruned_test <- cbind(
      word_count_vector_test
      , dtm_pruned_test
    )


    #### Bi-Gram and Prune the TestSet DTM

    AEM_Test = itoken(test_set$essay,
                      preprocessor = prep_fun,
                      tokenizer = tok_fun,
                      ids = test_set$examinee,
                      progressbar = FALSE
    )

    dtm_pruned_test_2 = create_dtm(AEM_Test, pruned_vectorizer_2)


    #### Prepend the Word Count

    dtm_pruned_test_2 <- cbind(
      word_count_vector_test
      , dtm_pruned_test_2
    )


    #### Reconcile Model Data Names

    dtm_pruned_train <- dtm_pruned_train_2
    dtm_pruned_test <- dtm_pruned_test_2


    # < Models > ####


    #### Fit Multiple Models with Caret

    set.seed(7)
    # caret_start_time = Sys.time()

    controls <- trainControl(
      method = "repeatedcv"
      , number = 10
      , repeats = 3
    )


    # 'model_select' to act as the condition on which to
    # determine which model to move ahead with


    if (model_select == 'dtmLM') {
      print("=============================================  Selected mode: dtmLM  =============================================")


      # dtmLM ####

      model_focus <- train(
        x = as.matrix(dtm_pruned_train)
        , y = training_set$score
        , method = "lm"
        , trControl = controls
      )

      predict_model <- predict(model_focus)
      PredictedScoresTest <- predict.train(model_focus, newdata = as.matrix(dtm_pruned_test))

      names(predict_model) <- NULL
      names(PredictedScoresTest) <- NULL

    } else if (model_select == 'dtmSVM') {

      print("=============================================  Selected mode: dtmSVM  =============================================")

      model_focus <- train(
        x = as.matrix(dtm_pruned_train)
        , training_set$score
        , method = "svmLinear2"
        , trControl = controls
        , scale = FALSE
        , verbose = TRUE
        , tuneGrid = data.frame(cost = c(.25, .5, 1))
        , preProc = c("center", "scale")
      )

      predict_model <- predict(model_focus)
      PredictedScoresTest <- predict.train(model_focus, newdata = as.matrix(dtm_pruned_test))

      names(predict_model) <- NULL
      names(PredictedScoresTest) <- NULL

    } else if (model_select == 'dtm_Cubist') {

      print("=============================================  Selected mode: dtm_Cubist  =============================================")

      model_focus <- train(
        x = as.matrix(dtm_pruned_train)
        , y = training_set$score
        , method = "cubist"
        , trControl = controls
        , scale = FALSE
        , verbose = TRUE
        , tuneGrid = expand.grid(
          .committees = c(1, 25, 50, 75, 100),
          .neighbors = c(0, 1, 5, 9)
        )
      )

      predict_model <- predict(model_focus)
      PredictedScoresTest <- predict.train(model_focus, newdata = as.matrix(dtm_pruned_test))

      names(predict_model) <- NULL
      names(PredictedScoresTest) <- NULL


    } else if (model_select == 'dtm_rrf') {

      print("=============================================  Selected mode: dtm_rrf  =============================================")


      model_focus <- train(
        x = as.matrix(dtm_pruned_train)
        , y = training_set$score
        , method = "RRFglobal"
        , trControl = controls
        , scale = FALSE
        , verbose = TRUE
        , tuneGrid = expand.grid(
          .mtry = c(2:5),
          .coefReg = c(0.1, .5, 1)
        )
      )

      predict_model <- predict(model_focus)
      PredictedScoresTest <- predict.train(model_focus, newdata = as.matrix(dtm_pruned_test))

      names(predict_model) <- NULL
      names(PredictedScoresTest) <- NULL


    } else if (model_select == 'dtm_SVMRadial') {

      print("=============================================  Selected mode: dtm_SVMRadial  =============================================")


      model_focus <- train(
        x = as.matrix(dtm_pruned_train)
        , y = training_set$score
        , method = "svmRadial"
        , trControl = controls
        , verbose = TRUE
        , preProc = c("center", "scale")
        , tuneGrid = expand.grid(
          .C = c(.25, .5, .75, 1),
          .sigma = c(0.01, 0.03, 0.05))
      )

      predict_model <- predict(model_focus)
      PredictedScoresTest <- predict.train(model_focus, newdata = as.matrix(dtm_pruned_test))

      names(predict_model) <- NULL
      names(PredictedScoresTest) <- NULL


    } else if (model_select == 'model.rf') {

      print("=============================================  Selected mode: model.rf  =============================================")


      model_focus <- train(
        x = as.matrix(dtm_pruned_train)
        , y = as.factor(training_set$score)
        , method = "rf"
        , ntree = 100
        , importance = TRUE
        , na.action = na.omit
        , trControl = controls
        , metric = "Accuracy"
        , allowParallel = TRUE
      )

      predict_model <- predict(model_focus)
      PredictedScoresTest <- predict.train(model_focus, newdata = as.matrix(dtm_pruned_test))

      names(predict_model) <- NULL
      names(PredictedScoresTest) <- NULL


    } else if (model_select == 'model.nnet') {

      print("=============================================  Selected mode: model.nnet  =============================================")


      model_focus <- train(
        x = as.matrix(dtm_pruned_train)
        , y = as.factor(training_set$score)
        , method = "nnet"
        , trace = TRUE
        , maxit = 1000
        , linout = 1
        , trControl = controls
        , preProcess = c('center', 'scale')
        , tuneGrid = expand.grid(
          .size = c(1:5),
          .decay = c(0.1, 0.3)
        )
      )

      predict_model <- predict(model_focus)
      PredictedScoresTest <- predict.train(model_focus, newdata = as.matrix(dtm_pruned_test))

      names(predict_model) <- NULL
      names(PredictedScoresTest) <- NULL


    } else if (model_select == 'dtm_GBM') {

      print("=============================================  Selected mode: dtm_GBM  =============================================")


      model_focus <- train(
        x = as.matrix(dtm_pruned_train)
        , y = training_set$score
        , method = "gbm"
        , trControl = controls
        , preProc = c("center", "scale")
        , tuneGrid = expand.grid(
          interaction.depth = 1:2
          , shrinkage = .1
          , n.trees = c(10, 50, 100)
          , n.minobsinnode = 10
        )
      )

      predict_model <- predict(model_focus)
      PredictedScoresTest <- predict.train(model_focus, newdata = as.matrix(dtm_pruned_test))


    } else if (model_select == 'dtm_avNNet') {

      print("=============================================  Selected mode: dtm_avNNet  =============================================")


      model_focus <- train(
        x = as.matrix(dtm_pruned_train)
        , y = training_set$score
        , method = "avNNet"
        , trControl = controls
        , scale = FALSE
        , verbose = TRUE
        , tuneGrid = expand.grid(
          size = c(1:5)
          , decay = c(0.1, 0.3)
          , bag = c(1)
        )
      )

      predict_model <- predict(model_focus)
      PredictedScoresTest <- predict.train(model_focus, newdata = as.matrix(dtm_pruned_test))

      names(predict_model) <- NULL
      names(PredictedScoresTest) <- NULL


    } else if (model_select == 'dtm_rrlda') {

      print("=============================================  Selected mode: dtm_rrlda  =============================================")


      model_focus <- train(
        x = as.matrix(dtm_pruned_train)
        , y = as.factor(training_set$score)
        , method = "rrlda"
        , trControl = controls

      )


      predict_model <- predict(model_focus)
      PredictedScoresTest <- predict.train(model_focus, newdata = as.matrix(dtm_pruned_test))

      names(predict_model) <- NULL
      names(PredictedScoresTest) <- NULL


    } else if (model_select == 'dtmQRF') {

      print("=============================================  Selected mode: dtmQRF  =============================================")


      model_focus <- train(
        x = as.matrix(dtm_pruned_train)
        , y = training_set$score
        , method = "qrf"
        , trControl = controls
        , scale = FALSE
        , verbose = TRUE
      )

      predict_model <- predict(model_focus)
      PredictedScoresTest <- predict.train(model_focus, newdata = as.matrix(dtm_pruned_test))

      names(predict_model) <- NULL
      names(PredictedScoresTest) <- NULL


    } else if (model_select == 'dtmQRNN') {

      print("=============================================  Selected mode: dtmQRNN  =============================================")


      model_focus <- train(
        x = as.matrix(dtm_pruned_train)
        , y = training_set$score
        , method = "qrnn"
        , trControl = controls
        , tuneLength = 4
        , trace = FALSE
        , iter.max = 50
        , n.trials = 2
        , tuneGrid = expand.grid(
          n.hidden = c(1:5)
          , penalty = c(1:2)
          , bag = c(1)
        )
      )

      predict_model <- predict(model_focus)
      PredictedScoresTest <- predict.train(model_focus, newdata = as.matrix(dtm_pruned_test))

      names(predict_model) <- NULL
      names(PredictedScoresTest) <- NULL


    } else if (model_select == 'glmnet') {

      print("=============================================  Selected mode: glmnet  =============================================")


      model_focus <- glmnet(
        x = as.matrix(dtm_pruned_train)
        , y = training_set$score
      )


      min_lambda <- sort(fit$lambda)[1]

      predict_model <- predict(
        object = model_focus
        , s = min_lambda
        , newx = as.matrix(dtm_pruned_test)
        , type = "response"
      )

      PredictedScoresTest <- round(unname(predict_model)[, 1])

      names(predict_model) <- NULL
      names(PredictedScoresTest) <- NULL


    } else if (model_select == 'glmnet_classifier_mae') {

      print("=============================================  Selected mode: glmnet_classifier_mae  =============================================")


      model_focus <- cv.glmnet(
        x = as.matrix(dtm_pruned_train)
        , y = training_set$score
        , family = 'gaussian'
        , alpha = 1
        , type.measure = "mae"
        , nfolds = NFOLDS
        , thresh = 1e-2
        , maxit = 1e2
        , lambda = lambda_seq
        , keep = TRUE
      )

      predict_model <- predict(
        object = model_focus
        , newx = as.matrix(dtm_pruned_test)
        , s = 'lambda.min'
        , type = 'response'
      )

      PredictedScoresTest <- round(unname(predict_model[, 1]))

      names(predict_model) <- NULL
      names(PredictedScoresTest) <- NULL


    } else if (model_select == 'glmnet_classifier_mse') {

      print("=============================================  Selected mode: glmnet_classifier_mse  =============================================")


      model_focus <- cv.glmnet(
        x = as.matrix(dtm_pruned_train)
        , y = training_set$score
        , family = 'gaussian'
        , alpha = 1
        , type.measure = "mse"
        , nfolds = NFOLDS
        , thresh = 1e-2
        , maxit = 1e2
        , lambda = lambda_seq
        , keep = T
      )

      predict_model <- predict(
        object = model_focus
        , newx = as.matrix(dtm_pruned_test)
        , s = 'lambda.min'
        , type = 'response'
      )

      PredictedScoresTest <- round(unname(predict_model[, 1]))

      names(predict_model) <- NULL
      names(PredictedScoresTest) <- NULL

    }

    print("=============================================  Finish  =============================================")


    # model_focus
    # predict_model
    # PredictedScoresTest

    return(tibble(
      scores = PredictedScoresTest
    ) %>%
             bind_cols(test_set %>% select(examinee, score)) %>%
             select(examinee, score, scores))
  }
}