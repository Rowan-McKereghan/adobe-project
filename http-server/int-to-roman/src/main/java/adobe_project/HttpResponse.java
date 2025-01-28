package adobe_project;

import java.util.Objects;

class HttpResponse {

    private int responseCode;
    private String responseBody;
    private String contentType;

    public HttpResponse(int code, String response, String type) {
        responseCode = code;
        responseBody = response;
        contentType = type;
    }

    public int getCode() {
        return responseCode;
    }

    public String getResponse() {
        return responseBody;
    }

    public String getType() {
        return contentType;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null)
            return false;
        if(obj.getClass() != this.getClass())
            return false;
        final HttpResponse o = (HttpResponse) obj;
        if((this.responseBody == null && o.getResponse() == null) && (this.contentType == null && o.getType() == null))
            return true;
        if(this.responseBody == null || this.contentType == null || o.getResponse() == null || o.getType() == null)
            return false;
        if(!this.responseBody.equals(o.getResponse()) || this.responseCode != o.getCode() || !this.contentType.equals(o.getType()))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.responseBody, this.responseCode, this.contentType);
    }

}