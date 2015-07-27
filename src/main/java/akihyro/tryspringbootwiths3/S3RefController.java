package akihyro.tryspringbootwiths3;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.WritableResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hoge")
public class S3RefController {

    @Autowired
    private ResourceLoader resourceLoader;

    @RequestMapping(method = RequestMethod.PUT)
    public void put(InputStream req) throws Exception {
        WritableResource resource = getResource();
        try (OutputStream out = resource.getOutputStream()) {
            copy(req, out);
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public void get(OutputStream res) throws Exception {
        Resource resource = getResource();
        try (InputStream in = resource.getInputStream()) {
            copy(in, res);
        }
    }

    private WritableResource getResource() {
        return (WritableResource)
                resourceLoader.getResource("s3://try-spring-boot-with-s3/hoge");
    }

    private void copy(InputStream in, OutputStream out) throws IOException {
        byte[] buff = new byte[1024];
        for (int len = in.read(buff); len > 0; len = in.read(buff)) {
            out.write(buff, 0, len);
        }
    }

}
