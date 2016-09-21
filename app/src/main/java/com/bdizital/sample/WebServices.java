package com.bdizital.sample;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * Created by OP Singh on 18-Apr-16.
 */
public class WebServices {

    public String funcGlobal(HashMap<String, String> hashMap, String urlT) {

        StringBuffer stringBuffer = new StringBuffer();
        String data = "";
        String adder = "";
        try {
            URL url = new URL(urlT);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);

            if (hashMap != null) {
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                for (String key : hashMap.keySet()) {
                    System.out.println("key : " + key);
                    System.out.println("value : " + hashMap.get(key));

                    stringBuffer.append(adder);
                    adder = "&";
                    stringBuffer.append(URLEncoder.encode(key, "UTF-8") + "=" + URLEncoder.encode(hashMap.get(key), "UTF-8"));
                }
                data = stringBuffer.toString();

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
            }

            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
            String return_data = "";
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                return_data += line;
            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
            return return_data;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String loginUser(String phone,String password, String urlT) {

        try {
            URL url = new URL(urlT);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String data = URLEncoder.encode("mobile_no", "UTF-8") + "=" + URLEncoder.encode(phone, "UTF-8")+"&"+
                    URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");

            bufferedWriter.write(data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
            String return_data = "";
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                return_data += line;
            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
            return return_data;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }


    public String signUpUser(String email,String password,String name, String number,String education, String speciality, String urlT) {

        try {
            URL url = new URL(urlT);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

            String data = URLEncoder.encode("uemail", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8")+"&"+
                    URLEncoder.encode("ppassword", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8")+"&"+
                    URLEncoder.encode("unmae", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8")+"&"+
                    URLEncoder.encode("mobile", "UTF-8") + "=" + URLEncoder.encode(number, "UTF-8")+"&"+
                    URLEncoder.encode("sseducation", "UTF-8") + "=" + URLEncoder.encode(education, "UTF-8")+"&"+
                    URLEncoder.encode("sspecility", "UTF-8") + "=" + URLEncoder.encode(speciality, "UTF-8");

            bufferedWriter.write(data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
            String return_data = "";
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                return_data += line;
            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
            return return_data;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }


















    public String saveGCMIdToServer(String _gcmId,String _deviceType ,String _registrationId, String urlT) {

        try {
            URL url = new URL(urlT);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String data = URLEncoder.encode("device_id", "UTF-8") + "=" + URLEncoder.encode(_gcmId, "UTF-8")+ "&" +
                    URLEncoder.encode("device_type", "UTF-8") + "=" + URLEncoder.encode(_deviceType, "UTF-8")+ "&" +
                    URLEncoder.encode("user_id", "UTF-8") + "=" + URLEncoder.encode(_registrationId, "UTF-8");

            bufferedWriter.write(data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
            String return_data = "";
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                return_data += line;
            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
            return return_data;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }


    public String fetchDocterApp(String dId, String urlT) {

        try {
            URL url = new URL(urlT);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String data = URLEncoder.encode("doctor_id", "UTF-8") + "=" + URLEncoder.encode(dId, "UTF-8");
            bufferedWriter.write(data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
            String return_data = "";
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                return_data += line;
            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
            return return_data;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }



    public String fetchDocterAppDetails(String appId, String urlT) {

        try {
            URL url = new URL(urlT);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String data = URLEncoder.encode("fixed_appointment_id", "UTF-8") + "=" + URLEncoder.encode(appId, "UTF-8");
            bufferedWriter.write(data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
            String return_data = "";
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                return_data += line;
            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
            return return_data;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }


    public String sendNumberForOtp(String phone, String urlT) {

        try {
            URL url = new URL(urlT);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String data = URLEncoder.encode("tmobile", "UTF-8") + "=" + URLEncoder.encode(phone, "UTF-8");

            bufferedWriter.write(data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
            String return_data = "";
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                return_data += line;
            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
            return return_data;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    public String verifyOtp (String phone, String otp, String urlT) {


        try {
            URL url = new URL(urlT);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String data = URLEncoder.encode("phoneno", "UTF-8") + "=" + URLEncoder.encode(phone, "UTF-8")+ "&" +
                    URLEncoder.encode("otpno", "UTF-8") + "=" + URLEncoder.encode(otp, "UTF-8");

            bufferedWriter.write(data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
            String return_data = "";
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                return_data += line;
            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
            return return_data;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    public String updatePassword (String phone, String password, String urlT) {


        try {
            URL url = new URL(urlT);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String data = URLEncoder.encode("phoneno", "UTF-8") + "=" + URLEncoder.encode(phone, "UTF-8")+ "&" +
                    URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");

            bufferedWriter.write(data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
            String return_data = "";
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                return_data += line;
            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
            return return_data;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    public String giveAppStatus(String status, String dRegId, String appId, String urlT) {

        try {
            URL url = new URL(urlT);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String data = URLEncoder.encode("status", "UTF-8") + "=" + URLEncoder.encode(status, "UTF-8")+ "&"+
            URLEncoder.encode("doctor_id", "UTF-8") + "=" + URLEncoder.encode(dRegId, "UTF-8")+ "&"+
            URLEncoder.encode("fixed_appointment_id", "UTF-8") + "=" + URLEncoder.encode(appId, "UTF-8");

            bufferedWriter.write(data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
            String return_data = "";
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                return_data += line;
            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
            return return_data;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    public String fetchAcceptedApp(String dRegId, String urlT) {

        try {
            URL url = new URL(urlT);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String data = URLEncoder.encode("doctor_id", "UTF-8") + "=" + URLEncoder.encode(dRegId, "UTF-8");
            bufferedWriter.write(data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
            String return_data = "";
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                return_data += line;
            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
            return return_data;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }



}
