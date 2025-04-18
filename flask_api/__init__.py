from flask import Flask
from .config import Config
from .extensions import db, limiter
from .routes.healthCheck import bp as health_bp
from .routes.city import bp as city_bp
from .routes.products import bp as products_bp
import os



def create_app():
    app = Flask(__name__)
    app.logger.info("Flask API Initialized")
    app.config.from_object(Config)

    db.init_app(app)
    limiter.init_app(app)

    app.register_blueprint(health_bp)
    app.register_blueprint(city_bp)
    app.register_blueprint(products_bp)

    return app