package fr.afc.dailyvet.model;

import io.grpc.Channel;

public class ApiHelper {
    private String token = "c6628f2ed2d840bfb71e6c6ad0c7afcc";

    public ApiHelper() {
    }

   /* private V2Grpc.V2BlockingStub getStub(){
         return V2Grpc.newBlockingStub(ClarifaiChannel.INSTANCE.getGrpcChannel())
                .withCallCredentials(new ClarifaiCallCredentials(token));
    }

    public void getResponseWithUrl(String photoURL){
        MultiOutputResponse response = getStub().postModelOutputs(PostModelOutputsRequest.newBuilder()
                .setModelId("996db7184634483293889871f42c3322")
                .addInputs(Input.newBuilder().setData(
                        Data.newBuilder().setImage(
                                Image.newBuilder().setUrl(photoURL)
                        )
                ))
                .build());
        if(response.getStatus().getCode() != StatusCode.SUCCESS){
            throw new RuntimeException("Request failed, status: " + response.getStatus());
        }
        for(Concept c : response.getOutputs(0).getData().getConceptsList()){
            System.out.println(String.format("%12s: %,.2f", c.getName(), c.getValue()));
        }
    }*/

}
