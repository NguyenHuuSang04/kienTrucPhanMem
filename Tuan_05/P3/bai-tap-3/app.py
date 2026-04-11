from flask import Flask
import os
import socket

app = Flask(__name__)


@app.route("/")
def home():
    instance_name = os.getenv("INSTANCE_NAME", "flask-instance")
    hostname = socket.gethostname()
    return {
        "message": "Hello from Flask",
        "instance": instance_name,
        "hostname": hostname,
    }


if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5000)