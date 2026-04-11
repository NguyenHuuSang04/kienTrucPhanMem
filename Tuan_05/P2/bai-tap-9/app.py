from flask import Flask

app = Flask(__name__)

@app.get('/')
def hello():
    return "<h1>Hello từ Flask bên trong Docker!</h1>"

if __name__ == "__main__":
    # Host 0.0.0.0 để có thể truy cập từ bên ngoài container
    app.run(host='0.0.0.0', port=5000)