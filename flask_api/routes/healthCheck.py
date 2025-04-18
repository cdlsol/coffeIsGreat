from flask import Blueprint, jsonify
from flask_api.middleware.api_key_auth import require_api_key

bp = Blueprint('health', __name__)

@bp.route('/health', methods=['GET'])
@require_api_key
def health():

    return jsonify({
        'status': 'healthy',
        'message': 'API is running fine'
    }), 200