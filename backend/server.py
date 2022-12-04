import mysql.connector
from flask import Flask, request, jsonify
from mysql.connector import Error, errorcode

app = Flask(__name__)



@app.route("/")
def index():
    return "Server Health: Good"


@app.route("/postImage", methods=["POST"])
def postImage():
    email = request.form["email"]
    image = request.form["image"]

    try:
        connection = mysql.connector.connect(host='localhost', database='smd', user='root', password='1234')
        cursor = connection.cursor()
        query = "INSERT INTO images (email, image) VALUES (%s, %s)"
        cursor.execute(query, (email, image))
        connection.commit()
        connection.close()
        return jsonify({"status": "success"})
    except Error as e:
        print(e)
        err = str(e)
        connection.close()
        return jsonify({"error": err})





if __name__ == "__main__":
    app.run(debug=True)
