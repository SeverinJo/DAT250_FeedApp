# AuthenticationEndpointsApi

All URIs are relative to *http://localhost:8080*

|Method | HTTP request | Description|
|------------- | ------------- | -------------|
|[**authenticate**](#authenticate) | **POST** /api/authenticate | |

# **authenticate**
> AuthenticationResponse authenticate(authenticationRequest)


### Example

```typescript
import {
    AuthenticationEndpointsApi,
    Configuration,
    AuthenticationRequest
} from './api';

const configuration = new Configuration();
const apiInstance = new AuthenticationEndpointsApi(configuration);

let authenticationRequest: AuthenticationRequest; //

const { status, data } = await apiInstance.authenticate(
    authenticationRequest
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **authenticationRequest** | **AuthenticationRequest**|  | |


### Return type

**AuthenticationResponse**

### Authorization

[bearerAuth](../README.md#bearerAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
|**200** | Login successful, access token returned |  -  |
|**401** | Invalid credentials or unauthorized access |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

