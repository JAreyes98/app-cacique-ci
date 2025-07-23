package com.cacique.caciqueci.business.rest.conf;



import static com.cacique.caciqueci.business.rest.conf.APIConfiguration.SECRET;

import android.text.TextUtils;

import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitFactory {

    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(APIConfiguration.BASE_API_URL)
            .addConverterFactory(GsonConverterFactory.create(
                    new GsonBuilder().serializeNulls().create()
            ));

    private static Retrofit retrofit = builder.build();

    //private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
            .connectTimeout(30, java.util.concurrent.TimeUnit.SECONDS) // 30 segundos para conexión
            .readTimeout(30, java.util.concurrent.TimeUnit.SECONDS)    // 30 segundos para leer
            .writeTimeout(30, java.util.concurrent.TimeUnit.SECONDS);  // 30 segundos para escribir


    public static <S> S createService(
            Class<S> serviceClass) {
        // String token = UserStaticInfo.getAuth();
        String token = SECRET;
        if (!TextUtils.isEmpty(token) ) {
            AuthenticationInterceptor interceptor =
                    new AuthenticationInterceptor(token);

            if (!httpClient.interceptors().contains(interceptor)) {
                httpClient.addInterceptor(interceptor);

                builder.client(httpClient.build());
                retrofit = builder.build();
            }
        }

        return retrofit.create(serviceClass);
    }
}
