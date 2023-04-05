kubectl delete -f quarkus-app-reliable/target/kubernetes/kubernetes.yml
mvn clean install
minikube image load mariofusco/quarkus-app-reliable:1.0-SNAPSHOT
kubectl apply -f quarkus-app-reliable/target/kubernetes/kubernetes.yml
