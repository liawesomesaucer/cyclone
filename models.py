from sqlalchemy import create_engine
from sqlalchemy import Column, Integer, String, Float, Boolean, DateTime
from utils import *
import utils
import datetime
from math import sqrt

from sqlalchemy.ext.declarative import declarative_base

Base = declarative_base()

# Standard user object
class User(Base):
	__tablename__ = 'users'

	# Total of 12 fields

	# Default, important user fields
	email = Column(String(100), primary_key=True)
	name = Column(String(50))
	password = Column(String(100))

	# Location based services
	longitude = Column(Float())
	latitude = Column(Float())

	# Personal info fields
	bio = Column(String(200))	# Also where tutors store credentials
	image = Column(String(200))	# Uri for image

	# If this guy is a tutor
	tutor = Column(Boolean())
	tutor_availability = Column(Boolean())	# If tutor is available for tutoring
	tutor_rating = Column(Float())
	tutor_fields = Column(String(5000))
	tutor_price = Column(Float())	# Tutor's desired price per hour


	def __init__ (	self, email, password, name, longitude=0.0, latitude=0.0, bio=None, image=None, tutor=False,
					tutor_availability=False, tutor_rating=0.0, tutor_fields="",
					tutor_price=0.0 ):

		self.email = email
		self.password = password
		self.name = name
		self.longitude = longitude
		self.latitude = latitude
		self.bio = bio
		self.image = image
		self.tutor = tutor
		self.tutor_availability = tutor_availability
		self.tutor_rating = tutor_rating
		self.tutor_fields = tutor_fields
		self.tutor_price = tutor_price

	def __repr__ ( self ):

		if self.tutor:			
			return "<Tutor %s>" % self.name
		return "<User %s>" % self.name

	def jsonify( self ):	# Converts to JSON

		obj = {
			"email" : self.email,
			"password" : self.password,
			"name": self.name,
			"longitude": self.longitude,
			"latitude": self.latitude,
			"bio": self.bio,
			"image": self.image,
			"tutor": self.tutor,
			"tutor_availability": self.tutor_availability,
			"tutor_rating": self.tutor_rating,
			"tutor_fields": self.tutor_fields,
			"tutor_price": self.tutor_price
		}

		return (obj)

	def distance( self, other ):

		if other.longitude==None and other.latitude==None:
			self.write("no longitude or latitude")
			raise

		return sqrt( ( self.longitude - other.longitude ) * ( self.longitude - other.longitude) + ( self.latitude - other.latitude ) * ( self.latitude - other.latitude) )

		# Distance formula = sqrt((x1-x2)^2 + (y1-y2)^2)

class TutoringSession(Base):
	__tablename__ = 'tutoring_sessions'

	tutor = Column(String(100), primary_key=True)
	user = Column(String(100))
	live = Column(Boolean())	# if session is still live

	duration = Column(Integer)	# in minutes

	startTime = Column(DateTime)

	def __init__( 	self, tutor, user, startTime=0, live=True, duration=0
					):

		self.tutor = tutor
		self.user = user
		self.startTime = datetime.datetime.utcnow
		self.live = live
		self.duration = duration

	def __repr__( self ):

		return "<%s Tutoring %s, started %s, live=%s>"

	def end( self ):	# ends the tutoring session

		self.live = False
		self.duration = datetime.datetime.utcnow - self.startTime