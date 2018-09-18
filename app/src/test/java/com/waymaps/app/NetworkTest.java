package com.waymaps.app;

import com.waymaps.data.model.RemoteTask;
import com.waymaps.data.remote.APIUtil;
import com.waymaps.data.remote.AppAPI;
import com.waymaps.data.remote.WayMapService;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class NetworkTest {
    /*@Test
    public void checkCallSms() {
        Call<RemoteTask[]> call = AppAPI.getWayMapService().callSms(APIUtil.Action.CALL, APIUtil.Name.SMS_SEND);
        try {
            Response<RemoteTask[]> execute = call.execute();
            Assert.assertTrue(execute.isSuccessful());
        } catch (Exception e) {
            assertTrue(false);
        }
    }*/

}