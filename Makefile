push-dev:
	./gradlew clean :pple-assets-interfaces:buildNeeded :pple-assets-interfaces:jib -Djib.to.image="106380540986.dkr.ecr.ap-northeast-2.amazonaws.com/pple-assets-dev" -Djib.to.credHelper="ecr-login"
