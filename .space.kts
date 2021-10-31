
job("Build and push Docker") {
    docker {
        // get auth data from secrets and put it to env vars
        env["DOCKERHUB_USER"] = Secrets("dockerhub_user")
        env["DOCKERHUB_TOKEN"] = Secrets("dockerhub_token")

        // put auth data to Docker config
        beforeBuildScript {
            content = """
                B64_AUTH=${'$'}(echo -n ${'$'}DOCKERHUB_USER:${'$'}DOCKERHUB_TOKEN | base64)
                echo "{\"auths\":{\"https://index.docker.io/v1/\":{\"auth\":\"${'$'}B64_AUTH\"}}}" > ${'$'}DOCKER_CONFIG/config.json
            """
        }

        build {
            labels["vendor"] = "tfg"
        }

        push("sergeydz/java-sample-service") {
            tags("1.0-space")
        }
    }
}