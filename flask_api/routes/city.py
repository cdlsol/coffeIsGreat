from flask import Blueprint, jsonify, current_app
from flask_api.extensions import db, limiter
from flask_api.models.city import City
from flask_api.middleware.api_key_auth import require_api_key
from sqlalchemy import distinct

bp = Blueprint('city', __name__)


@bp.route('/cities', methods = ['GET'])
@limiter.limit("100 per 30 hour")
@require_api_key

def get_cities():
    try:
        current_app.logger.info("Querying city dimension")

        result = (
            db.session.query(City.city)
            .distinct()
            .all()
        )

        current_app.logger.info("City query complete")
        data = [{'city': row.city} for row in result]
        current_app.logger.info("City data processed")
        return jsonify(data), 200
    except Exception as e:

        return jsonify({"error": str(e)}), 500