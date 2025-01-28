package adobe_project;

import java.util.Objects;

class HttpResponse {

    private int responseCode;
    private String responseBody;

    public HttpResponse(int code, String response) {
        responseCode = code;
        responseBody = response;
    }

    public int getCode() {
        return responseCode;
    }

    public String getResponse() {
        return responseBody;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null)
            return false;
        if(obj.getClass() != this.getClass())
            return false;
        final HttpResponse o = (HttpResponse) obj;
        if(this.responseBody == null && o.getResponse() == null)
            return true;
        if(!this.responseBody.equals(o.getResponse()) || this.responseCode != o.getCode())
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.responseBody, this.responseCode);
    }

}