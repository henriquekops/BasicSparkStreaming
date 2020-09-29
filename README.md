# BasicSparkStreaming
> Author: Henrique Kops

Basic spark streaming word count application using TCP data ingestion.

[Learn more about spark streaming](https://spark.apache.org/docs/latest/streaming-programming-guide.html)

## Build

Build project using *sbt*:
```sh
$ sbt package
```

## Usage

For local testing use two terminals

### Source terminal
NetCat to listen port 9999:

```sh
$ nc -lk 9999
hey hey hey dude
...
^C
$
```

### Stream processing terminal

Start processing:

```sh
$ ./run
...
^C
$
```

Output:
```sh
-------------------------------------
Time: 1601390430000 ms
-------------------------------------
(hey,3)
(dude,1)
```