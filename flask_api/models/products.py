from flask_api.extensions import db

class Product(db.Model):
    __tablename__ = 'dim_product'

    product_id = db.Column(db.Integer, primary_key = True)
    unit_price = db.Column(db.Numeric, nullable = False)
    category = db.Column(db.Text, nullable = False)
    product = db.Column(db.Text, nullable = False)