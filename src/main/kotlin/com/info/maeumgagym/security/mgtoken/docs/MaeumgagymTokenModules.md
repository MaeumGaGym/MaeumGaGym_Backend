# MaeumgagymTokenModules
인증용 토큰을 관리하기 위한 세 가지의 모듈들입니다.
1. 사용자 정보를 담은 data class를 암호화하는 [MaeumgagymTokenEncoder],
2. 클라이언트로부터 받은 토큰을 복호화해 data class로 전환하는 [MaeumgagymTokenDecoder],
3. 복호화한 토큰을 검증하는 [MaeumgagymTokenValidator]가 존재합니다.

# Why Modularization?
각각의 기능은 원래 [JwtAdapter], [JwtResolver]에 함수 형태로 분산되어 있었습니다.

하지만 이 설계는 토큰 관리 기능들에 대해 재사용성이 심각하게 낮았고, [JwtAdapter] 안에서도 중복 코드가 존재했습니다.

이를 해결하고자 기능들을 모아 세 개 인터페이스로 응집시켰습니다.

# Why not JWT?
Jwt 토큰은 Header, Payload, Signature의 세 부분으로 나뉩니다.

이것을 객체 지향적이지 않으며, 세 부분으로 나누는 것이 오히려 보안상 취약할 것이라고 보았습니다.

또한, 관련 기능들을 모듈화하는 과정에서, Jwts 클래스는 책임을 분할하기가 까다롭게 설계되어 있어 사용하기에는 부적합하다고 생각했습니다.

그렇기에 마음가짐만의 토큰을 만들었으며, 보다 객체 지향적이고 보안 중점적인 설계가 가능케 되었습니다.
