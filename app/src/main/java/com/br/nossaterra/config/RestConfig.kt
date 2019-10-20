package com.br.nossaterra.config

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import okhttp3.*
import okhttp3.OkHttpClient
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.*
import java.util.concurrent.TimeUnit
import javax.net.ssl.*

/**
 * Classe criada para realizar e construir requisições
 */
class RestConfig {

    private var client = OkHttpClient()
    private var json: String? = null
    private val builder = Request.Builder()
    private var url: String? = null
    private var response: Response? = null

    /**
     * Método criado para preparar uma requisição
     * @param url String
     */
    fun prepareRequest(url: String) {

        this.url = url
        builder.url(url)

    }

    /**
     * Método criado para o envio de dados
     * @param body RequestBody
     */
    fun post(body: RequestBody){
        this.builder.post(body)
    }

    /**
     * Método criado para a atualização de dados
     * @param body RequestBody
     */
    fun put(body: RequestBody){
        this.builder.put(body)
    }

    /**
     * Método criado para execução dos dados
     * @param instability Boolean
     * @return Response
     */
    fun execute(instability: Boolean = false): Response? {
        val request = builder.build()
        if (request.isHttps){
            var ok : OkHttpClient
            if(instability) ok = this.getUnsafeOkHttpClient(120) // HTTPS
            else ok = this.getUnsafeOkHttpClient() // HTTPS
            this.response = ok.newCall(request).execute()
        }
        else{
            this.response = client.newCall(request).execute()
        }
        return this.response
    }

    /**
     * Método criado para a coleta de uma String
     * @return String
     */
    fun getString(): String?{
        return this.response?.body()?.string()
    }

    /**
     * Método criado para a coleta de um Header
     * @return Headers
     */
    fun getHeaders(): Headers? {
        return this.response?.headers()
    }

    /**
     * Método criado para a coleta de um ResponseRequest
     * @param url String
     * @return Response
     */
    fun getResponseRequest(url: String): Response? {
        this.prepareRequest(url)
        return this.execute()
    }

    /**
     * Método cria um objeto do tipo Okhttp de certificado autoassinado
     * @param time Long
     * @return OKHttpClient
     */
    private fun getUnsafeOkHttpClient(time: Long = 60): OkHttpClient {
        try {
            // Create a trust manager that does not validate certificate chains
            val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                override fun getAcceptedIssuers(): Array<X509Certificate?> {
                    return arrayOfNulls(0)
                }

                @Throws(CertificateException::class)
                override fun checkClientTrusted(
                    chain: Array<X509Certificate>,
                    authType: String
                ) {
                }

                @Throws(CertificateException::class)
                override fun checkServerTrusted(
                    chain: Array<X509Certificate>,
                    authType: String
                ) {
                }
            })

            // Install the all-trusting trust manager
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, java.security.SecureRandom())
            // Create an ssl socket factory with our all-trusting manager
            val sslSocketFactory = sslContext.socketFactory

            return OkHttpClient.Builder()
                .connectTimeout(time, TimeUnit.SECONDS)
                .readTimeout(time, TimeUnit.SECONDS)
                .sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
                .hostnameVerifier(object : HostnameVerifier {
                    override fun verify(hostname: String, session: SSLSession): Boolean {
                        return true
                    }
                })
                .connectionSpecs(Arrays.asList(
                    ConnectionSpec.MODERN_TLS,
                    ConnectionSpec.COMPATIBLE_TLS
                )).build()

        } catch (e: Exception) {
            throw RuntimeException(e)
        }

    }

    /**
     * Método criado para adicionar um Header
     * @param key String
     * @param value String
     */
    fun addHeader(key: String, value: String){
        value.let {
            builder.addHeader(key, it)
        }
    }

    /**
     * Método criado para recuperar um ObjectJsonRequest
     * @param url String
     * @param instability Boolean
     * @return obj - Boolean
     */
    fun getJsonObject(json: String): JsonObject {
        val jparser = JsonParser()
        val jsonprimitive = Gson().toJsonTree(json).asJsonPrimitive
        val jsonobject = jparser.parse(jsonprimitive.asString).asJsonObject
        return jsonobject
    }

    fun getObjectJsonRequest(url: String, instability: Boolean = false): JsonObject{
        this.prepareRequest(url)
        this.execute(instability)
        val json = this.response?.body()?.string()
        val obj = getJsonObject(json!!)
        return obj
    }
}