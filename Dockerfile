FROM boboface/jdk:8u101
MAINTAINER zowbman <zowbman@hotmail.com>
ENV LANG zh_CN.UTF-8
COPY outer /app
VOLUME ["/app/work"]
EXPOSE 12001
ENTRYPOINT ["sh","-c"]
CMD ["/app/dcrun.sh"]
