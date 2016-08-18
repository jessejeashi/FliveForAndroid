from flask import Flask, jsonify,request
from flaskext.mysql import MySQL
import datetime

mysql = MySQL()
app = Flask(__name__)
app.config['MYSQL_DATABASE_USER'] = 'root'
app.config['MYSQL_DATABASE_PASSWORD'] = 'root'
app.config['MYSQL_DATABASE_DB'] = 'flive'
app.config['MYSQL_DATABASE_HOST'] = 'localhost'
mysql.init_app(app)


@app.route("/flive")
def hello():
    return "Welcome to Flive Chatroom!"

@app.route("/flive/get_flive")
def get_chatrooms():
    data = []
    cursor = mysql.connect().cursor()
    sql = "select * from flive where status = 1"
    try:
        cursor.execute(sql)
        results = cursor.fetchall()
        for row in results:
            temp = {}
            temp["chatroom_name"] = row[1]
            temp["topic"] = row[2]
            temp["user_name"] = row[3]
            temp["address"] = row[4]
            data.insert(0, temp)
    except:
        return jsonify(message="Unable to fecth data", status="ERROR")
    return jsonify(data=data, status="OK")

@app.route("/flive/get_address")
def get_address():
    data = []
    db = mysql.connect()
    cursor = db.cursor()
    sql = "select * from address"
    temp = {}
    try:
        cursor.execute(sql)
        results = cursor.fetchall()
        for row in results:
            address = row[0]
    except:
        return jsonify(message="Unable to fecth data", status="ERROR")
    newadd = int(address)+1
    temp["address"] = newadd
    data.insert(0, temp)
    sql1 = "update address set address = '"+str(newadd)+"'"
    cursor.execute(sql1)
    db.commit()
    db.close()
    return jsonify(data=data, status="OK")

@app.route("/flive/create_chatroom", methods=['GET','POST'])
def create_chatroom():
    chatroom_name = request.values.get("chatroom_name","")
    user_name = request.values.get("user_name","")
    topic = request.values.get("topic","")
    address = request.values.get("address","")
    db = mysql.connect()
    cursor = db.cursor()
    if chatroom_name.strip()=='':
        reply = "Invalid chatroom name"
        return jsonify(message=reply, status="ERROR")
    if user_name.strip()=='':
        reply = "Invalid user name"
        return jsonify(message=reply, status="ERROR")
    if topic.strip()=='':
        reply = "Topic should not be empty"
        return jsonify(message=reply, status="ERROR")
    if address.strip()=='':
        reply = "Video address should not be empty"
        return jsonify(message=reply, status="ERROR")
    else:
        sql = "insert into flive(chatroom_name,topic,user_name,address,status) values('"+chatroom_name+"','"+topic+"','"+user_name+"','"+address+"','1')"
        try:
            cursor.execute(sql)
            db.commit()
            return jsonify(status="OK")
        except:
            db.rollback()
            return jsonify(message="Access database failed", status="ERROR")
    db.close()

@app.route("/flive/close_chatroom", methods=['GET','POST'])
def close_chatroom():
    address = request.values.get("address","")
    db = mysql.connect()
    cursor = db.cursor()
    if address.strip()=='':
        reply = "Invalid chatroom address"
        return jsonify(message=reply, status="ERROR")
    else:
        sql = "update flive set status = 0 where address = '"+address+"'"
        try:
            cursor.execute(sql)
            db.commit()
            return jsonify(status="OK")
        except:
            db.rollback()
            return jsonify(message="Access database failed", status="ERROR")
    db.close()

if __name__ == "__main__":
    app.run(host="0.0.0.0")
