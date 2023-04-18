kubectl delete -f target/kubernetes/kubernetes.yml
mvn clean install
minikube image load mariofusco/drools-quarkus-reliable:1.0-SNAPSHOT
minikube mount $(pwd)/minikubetmp:/mnt/data --uid 185 --gid 0
kubectl apply -f target/kubernetes/kubernetes.yml
