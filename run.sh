#!/bin/bash
make && java -jar ./target/uberjar/thanassis-0.1.0-SNAPSHOT-standalone.jar "$@"
