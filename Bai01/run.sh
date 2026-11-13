#!/bin/bash

set -e

ROOT_DIR=$(cd "$(dirname "$0")" && pwd)

cd "$ROOT_DIR"

echo "========== RUN JUNIT TESTS =========="

# Chạy tất cả JUnit Test
mvn clean test

echo ""
echo "========== RUN MAIN PROGRAM =========="

# Chạy Main.java cùng các dependencies trong pom.xml
mvn exec:java -Dexec.mainClass=Main

echo ""
echo "========== ALL TASKS COMPLETED =========="