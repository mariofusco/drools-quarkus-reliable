minikube image load mariofusco/drools-quarkus-reliable:1.0-SNAPSHOT
minikube mount $(pwd)/minikubetmp:/mnt/data --uid 185 --gid 0

(checking the mount internal the node with)
minikube ssh
ls -la /mnt/data

kubectl apply -f target/kubernetes/kubernetes.yml

minikube tunnel

