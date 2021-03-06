package com.github.arthas.handlers.impl;

import com.github.arthas.handlers.IHttpMethod;
import com.github.arthas.http.ProxyMethodsDeclarations;
import com.github.arthas.models.StaticMetaInfo;
import org.springframework.web.reactive.function.client.WebClient;

import java.lang.reflect.Parameter;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.github.arthas.utils.ReflectParamsUtils.uri;

public final class BodyFluxRespMonoHttpMethodHandler implements IHttpMethod {

    private final StaticMetaInfo methodMetaInfo;

    public BodyFluxRespMonoHttpMethodHandler(StaticMetaInfo methodMetaInfo) {
        this.methodMetaInfo = methodMetaInfo;
    }

    @Override
    public Object method(WebClient webClient,  String baseUri, Object[] arguments, Parameter[] params) {
        Map<String, Integer> rawHeaders = this.methodMetaInfo.getHeaders();
        Map<String, String> headers = this.methodMetaInfo.getStaticHeaders();
        headers.putAll(rawHeaders.keySet().stream()
                .collect(Collectors.toMap(
                        Function.identity(), k -> (String) arguments[rawHeaders.get(k)])
                ));
        return ProxyMethodsDeclarations.bodyFluxRespMono(
                webClient,
                arguments[this.methodMetaInfo.getBodyPosition()],
                uri(
                        baseUri,
                        this.methodMetaInfo.getPathPattern(),
                        this.methodMetaInfo,
                        arguments
                ),
                headers
        ).apply(this.methodMetaInfo);
    }

}
