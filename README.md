# arthas
This is reactor http annotation client

Sorry for tests (1.0-SNAPSHOT). It's into next release. 


    @ArthasClient(
        url = "http://localhost:8080"
    )
    public interface IProxyInter {

        @Get(path = "/{content}")
        Flux<Models> sendGetRequest(
            @Path(name = "content") String content,
            @Query(name = "omg") String omg
        );

        @Post(path = "/{content}")
         Mono<Models> sendPostRequest(
            @Path(name = "content") String content,
            @Body Models payload
        );

    }
    
    IProxyInter pi = Arthas.builder().target(IProxyInter.class);
    Flux<Models> result = pi.sendGetRequest("ololo", "dsadsadasdasd232131231231231");
    Mono<Models> result1 = pi.sendPostRequest("model", new Models(1, "some data"));