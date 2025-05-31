FROM mcr.microsoft.com/devcontainers/java:1-21-bullseye

ARG INSTALL_MAVEN="false"
ARG MAVEN_VERSION=""

ARG INSTALL_GRADLE="false"
ARG GRADLE_VERSION=""

RUN if [ "${INSTALL_MAVEN}" = "true" ]; then su vscode -c "umask 0002 && . /usr/local/sdkman/bin/sdkman-init.sh && sdk install maven \"${MAVEN_VERSION}\""; fi \
    && if [ "${INSTALL_GRADLE}" = "true" ]; then su vscode -c "umask 0002 && . /usr/local/sdkman/bin/sdkman-init.sh && sdk install gradle \"${GRADLE_VERSION}\""; fi

# Carga SDKMAN! al iniciar el contenedor
RUN bash -c "source /usr/local/sdkman/bin/sdkman-init.sh"
# Añade SDKMAN al PATH para que esté disponible
ENV SDKMAN_DIR="/usr/local/sdkman"
ENV PATH="$SDKMAN_DIR/bin:$PATH"
RUN bash -c "source /usr/local/sdkman/bin/sdkman-init.sh && sdk install java 21.0.4-amzn"
RUN bash -c "source /usr/local/sdkman/bin/sdkman-init.sh && sdk install maven 3.9.9"

