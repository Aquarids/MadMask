package com.aquarids.madmask


class SimpleMockApi(@MockMethod mockMethod: String, regexStr: String) : MockApi(mockMethod, regexStr) {

    var successJsonPath: String = ""
    var errorJsonPath: String = ""

    private var mMockResult = MockApi.SUCCESS

    fun setMockResult(@MockResult mockResult: Int) {
        mMockResult = mockResult
    }

    fun getMockResult() = mMockResult
}