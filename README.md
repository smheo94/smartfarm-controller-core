# ```Smartfarm Environment Service``` 

# RELEASE NOTE
## 3.0.5
### 20200905
* CustomRemoteTokenServices 를 이용해서 서비스 Path 에 특정 경로가 포함된 경우 다른 Oauth 서버로 보내도록 함
* ServletFilter 에서 ```public static ThreadLocal<ServletRequest> threadLocalRequest = new ThreadLocal<>();``` 를 이용해서 
CustomRemoteTokenServices 에서 Request 를 가져다 쓸 수 있도록 함

# egov_web_default
전자정부 3.7버전의 Spring4 + Mybatis를 이용한 초기버전(No Component)
