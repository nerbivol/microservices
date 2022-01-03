#!/usr/bin/env bash

cd service-identity/

docker build -t nerbivol/service-identity:latest .
docker push nerbivol/service-identity

cd ..
cd service-declaration/

docker build -t nerbivol/service-declaration:latest .
docker push nerbivol/service-declaration

cd ..
cd service-visits/

docker build -t nerbivol/service-visits:latest .
docker push nerbivol/service-visits

cd ..
cd service-diagnostic/

docker build -t nerbivol/service-diagnostic:latest .
docker push nerbivol/service-diagnostic

cd ..
minikube stop && minikube delete
minikube start

cd service-identity/
kubectl apply -f service-identity.yaml

cd ..
cd service-declaration/
kubectl apply -f service-declaration.yaml

cd ..
cd service-visits/
kubectl apply -f service-visits.yaml

cd ..
cd service-diagnostic/
kubectl apply -f service-diagnostic.yaml

echo "$(minikube ip)"
