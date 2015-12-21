package akihyro.tryspringbootwiths3;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.WritableResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.util.StringUtils;

@RestController
@RequestMapping("/hoge")
public class S3RefController {
	@Autowired
	private AmazonS3 amazonS3Client;

	public void a() {
		String accessKey = "test:tester";
		String secretKey = "testing";

		AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
		AmazonS3 conn = new AmazonS3Client(credentials);
		conn.setEndpoint("http://192.168.8.80:8080");
		List<Bucket> buckets = conn.listBuckets();
		for (Bucket bucket : buckets) {
		        System.out.println(bucket.getName() + "\t" +
		                StringUtils.fromDate(bucket.getCreationDate()));
		}
	}

//    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private void resourceLoader(ResourceLoader resourceLoader) {
    	this.resourceLoader = resourceLoader;
    }

    @RequestMapping(method = RequestMethod.PUT)
    public void put(InputStream req) throws Exception {
        WritableResource resource = getResource();
        try (OutputStream out = resource.getOutputStream()) {
            copy(req, out);
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public void get(OutputStream res) throws Exception {
//    	a();
    	amazonS3Client.setEndpoint("http://192.168.8.80:8080");
        Resource resource = getResource();
        try (InputStream in = resource.getInputStream()) {
            copy(in, res);
        }
    }

    private WritableResource getResource() {
        return (WritableResource)
                resourceLoader.getResource("s3://test2/AAAA");
    }

    private void copy(InputStream in, OutputStream out) throws IOException {
        byte[] buff = new byte[1024];
        for (int len = in.read(buff); len > 0; len = in.read(buff)) {
            out.write(buff, 0, len);
        }
    }

}
