from flask_api.extensions import db

class City(db.Model):
    __tablename__ = 'dim_city'

    city_id = db.Column(db.Integer, primary_key = True)
    city = db.Column(db.Text, nullable = False)