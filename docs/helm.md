# Menu-service Helm chart

Chart directory: `helm/restaurant-menu`.

## What it creates

- A Deployment with one menu-service Pod on port 8081.
- A ClusterIP Service named after the Helm release. Use release name
  `restaurant-menu` so order-service can reach `http://restaurant-menu:8081`.
- A 1Gi persistent volume claim for the embedded H2 database at `/app/data`.

The Pod uses the existing GHCR image tagged
`sha-7952483082e8f0f6d5c14e966d13d60fb9a4bcae`. This was published by the
successful development workflow. Chart version is `0.1.0`; `appVersion` describes
the Maven application version. `image.tag` selects the actual image deployed.

Shared settings are in `values.yaml`; development/testing values are separate.
Namespace-local Services keep each environment's menu and order traffic together.
The same release name can be installed separately in development and testing.

H2 requires one application instance per database file. The chart uses one replica
and Recreate updates; do not scale it to share the file across multiple Pods.
The database starts separately from the earlier Docker volume and IntelliJ data.
The volume claim is retained if the Helm release is uninstalled, but a cluster
reset or explicit storage deletion can still remove the data.

## Validate

Run from menu-service's directory:

```powershell
helm lint ./helm/restaurant-menu --strict -f ./helm/restaurant-menu/values-development.yaml
helm template restaurant-menu ./helm/restaurant-menu --namespace development -f ./helm/restaurant-menu/values-development.yaml
helm lint ./helm/restaurant-menu --strict -f ./helm/restaurant-menu/values-testing.yaml
```

## Deploy development after validation

The namespace must contain the `ghcr-pull` registry Secret we created earlier.
The cluster's default StorageClass provisions the database claim.

```powershell
helm upgrade --install restaurant-menu ./helm/restaurant-menu --namespace development -f ./helm/restaurant-menu/values-development.yaml --wait --timeout 5m
kubectl get pods,svc,pvc -n development
kubectl logs -n development deployment/restaurant-menu
```

Readiness checks `/api/categories`; startup and liveness check the listening port.
No ingress or computer-facing port is allocated by this chart. For local access:

```powershell
kubectl port-forward -n development service/restaurant-menu 18081:8081
```

Keep that terminal open and visit http://localhost:18081/api/categories.
Unlike the earlier Docker containers, Kubernetes menu-service can be discovered
by Kubernetes order-service through its Service name.

## Testing later

After merging the selected feature branches directly into testing and selecting
the approved existing image tag:

```powershell
helm upgrade --install restaurant-menu ./helm/restaurant-menu --namespace testing -f ./helm/restaurant-menu/values-testing.yaml --wait --timeout 5m
```

We will automate these deployments through CD after manual deployment works.
