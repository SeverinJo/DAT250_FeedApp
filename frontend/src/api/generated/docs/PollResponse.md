# PollResponse


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**id** | **number** |  | [optional] [default to undefined]
**question** | **string** |  | [optional] [default to undefined]
**voteOptions** | [**Array&lt;VoteOptionResponse&gt;**](VoteOptionResponse.md) |  | [optional] [default to undefined]
**createdBy** | **string** |  | [optional] [default to undefined]
**validUntil** | **string** |  | [optional] [default to undefined]

## Example

```typescript
import { PollResponse } from './api';

const instance: PollResponse = {
    id,
    question,
    voteOptions,
    createdBy,
    validUntil,
};
```

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)
