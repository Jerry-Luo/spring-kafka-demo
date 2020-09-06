package com.spring.kafka.springkafkademo.sending.message.examples;

public class UsingKafkaTemplateDemo {

    // Non Blocking (Async):
    //
    //    public void sendToKafka(final MyOutputData data) {
    //        final ProducerRecord<String, String> record = createRecord(data);
    //
    //        ListenableFuture<SendResult<Integer, String>> future = template.send(record);
    //        future.addCallback(new KafkaSendCallback<SendResult<Integer, String>>() {
    //
    //            @Override
    //            public void onSuccess(SendResult<Integer, String> result) {
    //                handleSuccess(data);
    //            }
    //
    //            @Override
    //            public void onFailure(KafkaProducerException ex) {
    //                handleFailure(data, record, ex);
    //            }
    //
    //        });
    //    }


    // Blocking (Sync):
    //    public void sendToKafka(final MyOutputData data) {
    //        final ProducerRecord<String, String> record = createRecord(data);
    //
    //        try {
    //            template.send(record).get(10, TimeUnit.SECONDS);
    //            handleSuccess(data);
    //        }
    //        catch (ExecutionException e) {
    //            handleFailure(data, record, e.getCause());
    //        }
    //        catch (TimeoutException | InterruptedException e) {
    //            handleFailure(data, record, e);
    //        }
    //    }


}
