# AuthenticationTokenModules
인증용 토큰을 관리하기 위한 세 가지의 모듈들입니다.
1. 사용자 정보를 담은 data class를 암호화하는 AuthenticationTokenEncoder,
2. 클라이언트로부터 받은 토큰을 복호화해 data class로 전환하는 AuthenticationTokenDecoder,
3. 복호화한 토큰을 검증하는 AuthenticationTokenValidator가 존재합니다.

각각의 기능은 원래 JwtAdapter, JwtResolver에 함수 형태로 분산되어 있었습니다.

하지만 이 설계는 토큰 관리 기능들에 대해 재사용성이 심각하게 낮았고, JwtAdapter 안에서도 중복 코드가 존재했습니다.

이를 해결하기 위해 기능들을 세 개 인터페이스로 나누어 모듈화했으며, 구현체는 하나로 설계해 기능들 간에  무결성을 관리하기 쉽도록 설계했습니다.
