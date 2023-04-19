DEMO_NAMESPACE=drools-quarkus-reliable

minikube start --mount --mount-string="$(pwd)/minikubetmp:/mnt/data"

kubectl delete namespace --wait=true --ignore-not-found=true ${DEMO_NAMESPACE}
kubectl create namespace ${DEMO_NAMESPACE}

echo "Create drools-quarkus-reliable image"
mvn clean package -DskipTests
echo "Loading image to minikube"
minikube image load mariofusco/drools-quarkus-reliable:1.0-SNAPSHOT
echo "Setup drools-quarkus-reliable service"
kubectl apply -f target/kubernetes/kubernetes.yml -n ${DEMO_NAMESPACE}
echo "Waiting for service to deploy"
sleep 50
readonly SERVICE_URL=$(minikube service drools-quarkus-reliable --url  -n drools-quarkus-reliable )
readonly SESSION_REQUEST=$(curl -LI -X POST -H 'Accept: application/json' -H 'Content-Type: application/json' ${SERVICE_URL}/session -o /dev/null -w '%{http_code}' -s)

if [ "200" != ${SESSION_REQUEST} ]; then
  echo "Failed to get session ID"
  minikube logs
  exit 1
  fi

echo "Drools session is available by URL: ${SERVICE_URL}"