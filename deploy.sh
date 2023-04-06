kubectl delete -f target/kubernetes/kubernetes.yml
mvn clean install
minikube image load mariofusco/drools-quarkus-reliable:1.0-SNAPSHOT
kubectl apply -f target/kubernetes/kubernetes.yml
