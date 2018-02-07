FROM node:9.4.0
RUN echo "deb http://ftp.debian.org/debian jessie-backports main" >> /etc/apt/sources.list
RUN apt-get update && \
    apt-get install -y --no-install-recommends \
    chromium \
    libgconf-2-4
RUN apt-get -t jessie-backports install -y --no-install-recommends openjdk-8-jdk-headless \
    && rm -rf /var/lib/apt/lists/*

ENV CHROME_BIN /usr/bin/chromium
WORKDIR /usr/src/app
