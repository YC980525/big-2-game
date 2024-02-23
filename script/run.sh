rm -r out/
cd src
javac -cp . Main.java -d ../out/
cd ..
java -cp out/ Main
