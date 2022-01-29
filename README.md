# pple-assets

## 고도화 방향
* 다수의 인스턴스에서 요청을 처리할 경우 동시성 이슈를 없애기 위해 메시징 큐 혹은 분산락 도입 필요(운영 배포 전 도입 TBU)
  * https://github.com/heli-os/kotlin-springboot-distributed-lock
