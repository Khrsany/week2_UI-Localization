-- Luodaan tietokanta utf8mb4-tuella, jotta Japani ja Persia toimivat oikein
CREATE DATABASE IF NOT EXISTS fuel_calculator_localization
CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE fuel_calculator_localization;

-- Taulu laskuhistorialle tehtävänannon mukaisesti
CREATE TABLE IF NOT EXISTS calculation_records (
    id INT AUTO_INCREMENT PRIMARY KEY,
    distance DOUBLE NOT NULL,
    consumption DOUBLE NOT NULL,
    price DOUBLE NOT NULL,
    total_fuel DOUBLE NOT NULL,
    total_cost DOUBLE NOT NULL,
    language VARCHAR(10),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Taulu käännösteksteille
CREATE TABLE IF NOT EXISTS localization_strings (
    id INT AUTO_INCREMENT PRIMARY KEY,
    `key` VARCHAR(100) NOT NULL,
    value VARCHAR(255) NOT NULL,
    language VARCHAR(10) NOT NULL,
    UNIQUE KEY unique_key_lang (`key`, language)
);

-- Lisätään kaikki neljä vaadittua kieltä: EN, FR, JP, IR
INSERT IGNORE INTO localization_strings (language, `key`, value) VALUES
('en', 'distance.label', 'Distance (km)'),
('en', 'consumption.label', 'Fuel Consumption (L/100 km)'),
('en', 'price.label', 'Fuel Price (per liter)'),
('en', 'calculate.button', 'Calculate'),
('en', 'result.placeholder', 'Result will appear here'),
('en', 'result.label', 'Total fuel needed: {0} L | Total cost: {1}'),
('en', 'invalid.input', 'Invalid input'),

('fr', 'distance.label', 'Distance (km)'),
('fr', 'consumption.label', 'Consommation de carburant (L/100 km)'),
('fr', 'price.label', 'Prix du carburant (par litre)'),
('fr', 'calculate.button', 'Calculer'),
('fr', 'result.placeholder', 'Le résultat apparaîtra ici'),
('fr', 'result.label', 'Carburant nécessaire : {0} L | Coût total : {1}'),
('fr', 'invalid.input', 'Entrée invalide'),

('ja', 'distance.label', '距離 (km)'),
('ja', 'consumption.label', '燃費 (L/100 km)'),
('ja', 'price.label', '燃料価格 (1リッターあたり)'),
('ja', 'calculate.button', '計算する'),
('ja', 'result.placeholder', 'ここに結果が表示されます'),
('ja', 'result.label', '必要な燃料: {0} L | 合計コスト: {1}'),
('ja', 'invalid.input', '無効な入力'),

('fa', 'distance.label', 'مسافت (کیلومتر)'),
('fa', 'consumption.label', 'مصرف سوخت (لیتر در ۱۰۰ کیلومتر)'),
('fa', 'price.label', 'قیمت سوخت (هر لیتر)'),
('fa', 'calculate.button', 'محاسبه'),
('fa', 'result.placeholder', 'نتیجه اینجا ظاهر می‌شود'),
('fa', 'result.label', 'سوخت مورد نیاز: {0} لیتر | هزینه کل: {1}'),
('fa', 'invalid.input', 'ورودی نامعتber');