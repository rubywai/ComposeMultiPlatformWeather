import 'dart:io';

void main() {
  // Specify the path to the "web" directory
  var pathToServe = 'web';

  // Bind the server to localhost on port 8080
  HttpServer.bind(InternetAddress.loopbackIPv4, 8080).then((server) {
    print('Server running on localhost:${server.port}');

    // Listen for requests
    server.listen((HttpRequest request) {
      // Construct the file path from the request URI
      var file = File('$pathToServe${request.uri.path}');

      // Check if the file exists
      file.exists().then((bool exists) {
        if (exists) {
          // Serve the file
          file.openRead().pipe(request.response);
        } else {
          // File not found, return a 404 response
          request.response.statusCode = HttpStatus.notFound;
          request.response.write('File not found');
          request.response.close();
        }
      });
    });
  });
}
