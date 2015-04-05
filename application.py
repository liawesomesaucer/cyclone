import os
import tornado.web

from sqlalchemy.orm import scoped_session, sessionmaker
from models import *
from utils import *
from db import *
import utils

import json
testUser = {
	"name" : "Jarnin",
	"email" : "jfang@jfang.edu",
	"password" : "123",
	"description" : "Stuff stuff stuff",
	"profile_pic" : "http://upload.wikimedia.org/wikipedia/commons/thumb/8/85/Smiley.svg/2000px-Smiley.svg.png",
	"interests" : "Eating Badminton Gymming",
	"classes" : "CSE20 CSE30",
	"yesUsers" : "",
	"noUsers" : "",
	"rating" : "4.9",
	"rating_times" : "10",
	"location" : "temporaryvariable"
}	# test user for json

print("current engine is %s" % engine)
print("current session is %s" % session)

class BaseHandler(tornado.web.RequestHandler):
	def set_default_headers(self):
		def db(self):
			return self.application.db

		# Get the current user from the db
		def get_current_user(self):
			user_id = self.get_secure_cookie("user")
			if not user_id: 
				return None
			return self.db.query(User).get(user_id)
			
		self.set_header("Access-Control-Allow-Origin", 
			"http://liawesomesaucer.github.io")

# This is a test for hello world
class HelloHandler(BaseHandler):
	def get(self):
		try:
			self.write(json.dumps(testUser))
		except:
			print("Hello failed")
			raise
	def post(self):
		try:
			stuff = self.get_argument('stuff', '')
			self.write("you sent me: " + stuff)
		except:
			raise
			self.write("something horrible happened")

class TestHandler(BaseHandler):		# (self, test) test returns testname

	def get(self, test):
		
		try:
			self.write("this is test %s" % test)
		except:
			print("Test failed")
			raise

class JsonHandler(BaseHandler):

	def post(self):
		try:
			data = tornado.escape.json_decode(self.request.body)
			# self.write(data)
			# self.write(data["stuff"])
			self.write(self.request.body)
		except:
			print("json test failed")
			raise
			self.write("fail")

class LoginTestHandler(BaseHandler):	# takes login stuff (needs change)
	def get(self, email, password):
		try:
			self.write("try to login here")
		except:
			print("failed2")
			raise
class LoginOnlyHandler(BaseHandler):	# redirects if not logged in
	def get(self):
		if not self.current_user:
			self.redirect("/")
		try:
			self.write("congrats you are a registered user")
		except:
			print("failed")
			raise

# class DistanceHandler(BaseHandler):

# 	def post(self):
# 		try:
# 			data = tornado.escape.json_decode(self.request.body)

# 			self_email = data["self_email"]
# 			self_longitude = data["self_longitude"]

# 			other_email = data["other_email"]

# 			if not self_email or not other_email:

# User Creation - post to db
class CreateUserHandler(BaseHandler):

	def post(self):
		try:
			
			data = tornado.escape.json_decode(self.request.body)

			email = data["email"]
			password = data["password"]
			name = data["name"]

			print("[NAME, EMAIL, and PASSWORD below]")
			print(name, email, password)	#debug

			if not name or not email or not password:
				print("name, email, or password is null")
				self.write("0")
				return
		
			if data["longitude"] and data["latitude"]:
				longitude = data["longitude"]
				latitude = data["latitude"]
				new_user = User (email, password, name, latitude, longitude)

			else:
				new_user = User (email, password, name)

			# add this guy to session
			session.add(new_user)
			session.commit()

			print("just added: %s" % new_user)
			self.write("1")

		except:
			print("create_user failed")
			raise
			self.write("-1")

class LoginUserHandler(BaseHandler):

	def post(self):

		try:
			data = tornado.escape.json_decode(self.request.body)
			email, password = data["email"], data["password"]
			longitude, latitude = data["longitude"], data["latitude"]

			if not email or not password:
				self.write("0")
				return

			this_user = session.query(User).filter_by(email=email).filter_by(password=password).first()
			if this_user:
				this_user.longitude, this_user.latitude = longitude, latitude
				self.write(this_user.jsonify())
				print("Logging in %s" % email)
				return
			else:
				self.write("0")
				return

		except:
			print("login_user failed")
			raise
			self.write("-1")


class AllUserHandler(BaseHandler):

	def post(self):
		try:
			data = tornado.escape.json_decode(self.request.body)
			email = data["email"]
			if not email:
				self.write("0")
				return

			current_user = session.query(User).filter_by(email=email).first()

			if not current_user:
				self.write("0")
				return

			users = session.query(User).all()

			final_users = []	# users to send
			for user in users:
				if user is not current_user:
					distance = current_user.distance(user)

					if distance < 0.1:	# 7 mile radius
						final_users.append(user.jsonify())

			if final_users:
				self.write(str(final_users))

			else:
				self.write("[]")

		except:
			print("all user query failed")
			raise
			self.write("-1")

class Application(tornado.web.Application):
	def __init__(self):
		
		handlers = [
			(r"/", 				HelloHandler),
			(r"/test/([^/]+)", 	TestHandler),
			(r"/login", 		LoginTestHandler),
			(r"/loggedin",		LoginOnlyHandler),
			(r"/create_user",	CreateUserHandler),
			(r"/login_user",	LoginUserHandler),
			(r"/json_test",		JsonHandler),
			(r"/all_users",		AllUserHandler)
		]

		#self.db = scoped_session(sessionmaker(bind=engine))

		tornado.web.Application.__init__(self, handlers)
