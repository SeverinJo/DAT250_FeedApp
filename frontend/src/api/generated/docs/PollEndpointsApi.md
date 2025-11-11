# PollEndpointsApi

All URIs are relative to *http://localhost:8080*

|Method | HTTP request | Description|
|------------- | ------------- | -------------|
|[**createPoll**](#createpoll) | **POST** /api/polls | |
|[**getPolls**](#getpolls) | **GET** /api/polls | |
|[**vote**](#vote) | **POST** /api/polls/{pollId}/vote-options/{voteOptionId}/vote | |

# **createPoll**
> createPoll(pollCreationRequest)


### Example

```typescript
import {
    PollEndpointsApi,
    Configuration,
    PollCreationRequest
} from './api';

const configuration = new Configuration();
const apiInstance = new PollEndpointsApi(configuration);

let pollCreationRequest: PollCreationRequest; //

const { status, data } = await apiInstance.createPoll(
    pollCreationRequest
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **pollCreationRequest** | **PollCreationRequest**|  | |


### Return type

void (empty response body)

### Authorization

[bearerAuth](../README.md#bearerAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
|**204** | Creation successful |  -  |
|**400** | Bad request |  -  |
|**401** | Unauthorized |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **getPolls**
> Array<PollResponse> getPolls()


### Example

```typescript
import {
    PollEndpointsApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new PollEndpointsApi(configuration);

let onlyMyPolls: boolean; // (optional) (default to false)

const { status, data } = await apiInstance.getPolls(
    onlyMyPolls
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **onlyMyPolls** | [**boolean**] |  | (optional) defaults to false|


### Return type

**Array<PollResponse>**

### Authorization

[bearerAuth](../README.md#bearerAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
|**200** | Get successful, polls returned |  -  |
|**400** | Bad request |  -  |
|**401** | Unauthorized |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **vote**
> vote()


### Example

```typescript
import {
    PollEndpointsApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new PollEndpointsApi(configuration);

let pollId: number; // (default to undefined)
let voteOptionId: number; // (default to undefined)

const { status, data } = await apiInstance.vote(
    pollId,
    voteOptionId
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **pollId** | [**number**] |  | defaults to undefined|
| **voteOptionId** | [**number**] |  | defaults to undefined|


### Return type

void (empty response body)

### Authorization

[bearerAuth](../README.md#bearerAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
|**204** | Voted successful |  -  |
|**400** | Bad request |  -  |
|**401** | Unauthorized |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

