# UserEndpointsApi

All URIs are relative to *http://localhost:8080*

|Method | HTTP request | Description|
|------------- | ------------- | -------------|
|[**createUser**](#createuser) | **POST** /api/user/new-user | |
|[**getUserInformation**](#getuserinformation) | **GET** /api/user | |

# **createUser**
> UserInformationResponse createUser(userCreationRequest)


### Example

```typescript
import {
    UserEndpointsApi,
    Configuration,
    UserCreationRequest
} from './api';

const configuration = new Configuration();
const apiInstance = new UserEndpointsApi(configuration);

let userCreationRequest: UserCreationRequest; //

const { status, data } = await apiInstance.createUser(
    userCreationRequest
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **userCreationRequest** | **UserCreationRequest**|  | |


### Return type

**UserInformationResponse**

### Authorization

[bearerAuth](../README.md#bearerAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
|**201** | New user account created |  -  |
|**400** | Invalid request |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **getUserInformation**
> UserInformationResponse getUserInformation()


### Example

```typescript
import {
    UserEndpointsApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new UserEndpointsApi(configuration);

const { status, data } = await apiInstance.getUserInformation();
```

### Parameters
This endpoint does not have any parameters.


### Return type

**UserInformationResponse**

### Authorization

[bearerAuth](../README.md#bearerAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
|**200** | Get successful, user information for current user returned |  -  |
|**401** | Unauthorized |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

