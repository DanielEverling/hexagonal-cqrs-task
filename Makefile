MVN_CMD				:= mvn -T 4C
PROJECT_NAME		?= $(shell basename `pwd` | tr '[:upper:]' '[:lower:]' | tr -d '[:punct:]')
DOCKER_NETWORK		?= $(PROJECT_NAME)_default
MAVEN_DIR			?= $(HOME)/.m2
WORKING_DIR			?= $(shell pwd)

.DEFAULT_GOAL := help

install: ## Executes mvn clean install to run the full maven lifecycle
	@$(MVN_CMD) clean install -U

fast-install: ## Same as install, but does execute tests or static analysis
	@$(MVN_CMD) clean install -U -DskipTests=true

## Tip from https://carlosbecker.com/posts/maven-dependency-hell/
dep-multiple-version: ## Lists dependencies that have multiple versions and are in conflict
	@mvn dependency:list -Dsort=true | \
                 grep "^\[INFO\]    " | \
                 awk '{print $$2}' | \
                 cut -f1-4 -d: | \
                 sort | \
                 uniq | \
                 cut -f1-3 -d: | \
                 uniq -d

up: ## Starts applications and dependencies
	@docker-compose --project-name $(PROJECT_NAME) up -d --build

logs: ## Shows applications and dependencies logs
	@docker-compose --project-name $(PROJECT_NAME) logs -f

down: ## Shutsdown applications and dependencies
	@docker-compose --project-name $(PROJECT_NAME) down || true
	@docker-compose --project-name $(PROJECT_NAME) kill || true
	@docker-compose --project-name $(PROJECT_NAME) rm -f || true

integration-test: ## Executes integration-tests from a docker container
	@docker run --rm \
		--network $(DOCKER_NETWORK) \
		-v $(MAVEN_DIR):/root/.m2 \
		-v $(WORKING_DIR):$(WORKING_DIR) \
		-w $(WORKING_DIR) \
		maven:3.5.3-jdk-8 \
		mvn -B test-compile failsafe:integration-test failsafe:verify

rebuild-and-up: down fast-install up ## Rebuild and up the entire application

## Tip from https://marmelab.com/blog/2016/02/29/auto-documented-makefile.html
help:
	@grep -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | sort | awk 'BEGIN {FS = ":.*?## "}; {printf "\033[36m%-30s\033[0m %s\n", $$1, $$2}'

.PHONY: fast-install install dep-multiple-version up logs down integration-test rebuild-and-up help