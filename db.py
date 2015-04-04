from sqlalchemy import create_engine, MetaData
from sqlalchemy.orm import sessionmaker

DB_URL = 'postgres://svzgijjedzoxgo:lqiOequvQPlltINbMVmdLKKf1e@ec2-54-163-235-165.compute-1.amazonaws.com:5432/dct7rhlbci5ha1'
engine = create_engine(DB_URL, echo=True)

Session = sessionmaker(bind=engine)
session = Session()

meta = MetaData()
meta.create_all(engine)