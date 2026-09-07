package com.yogaflow.data.db

import com.yogaflow.data.model.FavoritePose
import com.yogaflow.data.model.PlanPoseItem
import com.yogaflow.data.model.PracticeCompletion
import com.yogaflow.data.model.TargetPlan
import com.yogaflow.data.model.UserProfile
import com.yogaflow.data.model.YogaPose
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object InitialData {

    val defaultProfile = UserProfile(
        id = "user_default",
        email = "practitioner@yogaflow.app",
        name = "Elena",
        age = 29,
        gender = "Female",
        level = "Beginner",
        goals = listOf("Stress Relief", "Better Sleep", "Flexibility"),
        createdAt = System.currentTimeMillis()
    )

    val poses = listOf<YogaPose>(
        YogaPose(
            slug = "balancing_tree_pose",
            name = "Low Lunge (Prayer Hands)",
            sanskritName = "Anjaneyasana",
            category = "Standing",
            targets = listOf("Flexibility", "Energy"),
            difficulty = "Beginner",
            durationSeconds = 60,
            instructions = listOf(
                "From downward dog, step one foot forward between your hands.",
                "Lower your back knee to the mat and untuck your toes.",
                "Bring your hands to your heart in a prayer position and lift your chest."
            ),
            benefits = listOf(
                "Stretches the hip flexors and quadriceps",
                "Improves balance and opens the chest"
            ),
            breathingCue = "Inhale to lift the chest; exhale to sink deeper into the hips.",
            modifications = "Place a blanket under the back knee for cushioning.",
            pregnancyAllowed = true,
            iconType = "standing"
        ),
        YogaPose(
            slug = "boat_pose",
            name = "Corpse Pose",
            sanskritName = "Savasana",
            category = "Restorative",
            targets = listOf("Sleep", "Stress Relief"),
            difficulty = "Beginner",
            durationSeconds = 300,
            instructions = listOf(
                "Lie flat on your back with legs extended.",
                "Allow your feet to fall open naturally.",
                "Rest your arms by your sides with palms facing up."
            ),
            benefits = listOf(
                "Completely relaxes the central nervous system",
                "Reduces stress, fatigue, and blood pressure"
            ),
            breathingCue = "Allow the breath to return to its natural, effortless rhythm.",
            modifications = "Place a bolster under your knees to relieve lower back tension.",
            pregnancyAllowed = true,
            iconType = "restorative"
        ),
        YogaPose(
            slug = "bow_pose",
            name = "Downward-Facing Dog",
            sanskritName = "Adho Mukha Svanasana",
            category = "Inversion",
            targets = listOf("Flexibility", "Energy"),
            difficulty = "Beginner",
            durationSeconds = 60,
            instructions = listOf(
                "Start on all fours, tuck your toes, and lift your hips high and back.",
                "Press firmly through your hands, keeping your fingers spread wide.",
                "Pedal your feet to stretch the hamstrings, eventually reaching heels toward the floor."
            ),
            benefits = listOf(
                "Stretches the hamstrings, calves, and Achilles tendons",
                "Strengthens the arms, shoulders, and back"
            ),
            breathingCue = "Inhale to lengthen the spine; exhale to root down through the heels.",
            modifications = "Keep a slight bend in the knees if hamstrings are tight.",
            pregnancyAllowed = true,
            iconType = "inversion"
        ),
        YogaPose(
            slug = "bridge_pose",
            name = "Crescent Moon Pose",
            sanskritName = "Anjaneyasana Variation",
            category = "Backbend",
            targets = listOf("Flexibility", "Energy"),
            difficulty = "Intermediate",
            durationSeconds = 60,
            instructions = listOf(
                "From a low lunge, lower your back knee to the floor.",
                "Inhale, sweep your arms up and arch your back slightly.",
                "Keep the chest lifted and shoulders relaxed."
            ),
            benefits = listOf(
                "Deeply stretches hip flexors",
                "Opens the heart and chest space"
            ),
            breathingCue = "Inhale to expand the chest; exhale to sink into the hips.",
            modifications = "Keep hands on the front thigh instead of reaching back.",
            pregnancyAllowed = true,
            iconType = "backbend"
        ),
        YogaPose(
            slug = "camel_pose",
            name = "Constructive Rest",
            sanskritName = "Savasana Variation",
            category = "Restorative",
            targets = listOf("Stress Relief", "Sleep"),
            difficulty = "Beginner",
            durationSeconds = 180,
            instructions = listOf(
                "Lie flat on your back.",
                "Bend your knees and place your feet flat on the mat, hip-width apart.",
                "Rest your hands gently on your belly or by your sides."
            ),
            benefits = listOf(
                "Releases lower back tension",
                "Calms the nervous system gently"
            ),
            breathingCue = "Breathe deeply into the belly, feeling it rise and fall.",
            modifications = "Let your knees knock together for less effort.",
            pregnancyAllowed = true,
            iconType = "restorative"
        ),
        YogaPose(
            slug = "chair_pose",
            name = "Extended Side Angle",
            sanskritName = "Utthita Parsvakonasana",
            category = "Standing",
            targets = listOf("Energy", "Flexibility"),
            difficulty = "Beginner",
            durationSeconds = 60,
            instructions = listOf(
                "From Warrior II, hinge forward at the hips.",
                "Bring your front hand to the floor or a block outside your front foot.",
                "Extend your top arm over your ear in a straight diagonal line."
            ),
            benefits = listOf(
                "Strengthens legs and core",
                "Stretches the side body from heel to fingertips"
            ),
            breathingCue = "Inhale to lengthen the side body; exhale to rotate the chest upward.",
            modifications = "Rest your forearm on your front thigh instead of reaching for the floor.",
            pregnancyAllowed = true,
            iconType = "standing"
        ),
        YogaPose(
            slug = "cobra_pose",
            name = "Triangle Pose",
            sanskritName = "Utthita Trikonasana",
            category = "Standing",
            targets = listOf("Flexibility", "Energy"),
            difficulty = "Beginner",
            durationSeconds = 60,
            instructions = listOf(
                "Step your feet wide apart, turning your front foot out 90 degrees.",
                "Extend your arms parallel to the floor and reach forward.",
                "Hinge at the hip, bringing your bottom hand to your shin or floor, and reach your top hand up."
            ),
            benefits = listOf(
                "Stretches the hips, groins, and hamstrings",
                "Opens the chest and shoulders"
            ),
            breathingCue = "Inhale to lengthen the spine; exhale to rotate the chest toward the sky.",
            modifications = "Rest your bottom hand on a block.",
            pregnancyAllowed = true,
            iconType = "standing"
        ),
        YogaPose(
            slug = "constructive_rest_pose",
            name = "Handstand (Stag Legs)",
            sanskritName = "Adho Mukha Vrksasana Variation",
            category = "Inversion",
            targets = listOf("Mental Focus", "Energy"),
            difficulty = "Advanced",
            durationSeconds = 30,
            instructions = listOf(
                "Kick up into a handstand.",
                "Bend one knee deeply while keeping the other leg extended.",
                "Engage your core to maintain balance."
            ),
            benefits = listOf(
                "Develops immense core and shoulder strength",
                "Improves intense spinal flexibility and balance"
            ),
            breathingCue = "Inhale into the chest to lift; exhale to maintain core stability.",
            modifications = "Practice against a wall.",
            pregnancyAllowed = false,
            iconType = "inversion"
        ),
        YogaPose(
            slug = "corpse_pose",
            name = "Chair Pose",
            sanskritName = "Utkatasana",
            category = "Standing",
            targets = listOf("Energy", "Mental Focus"),
            difficulty = "Beginner",
            durationSeconds = 60,
            instructions = listOf(
                "Stand with feet together or hip-width apart.",
                "Inhale to raise your arms alongside your ears.",
                "Exhale and bend your knees, sinking your hips down and back."
            ),
            benefits = listOf(
                "Strengthens the thighs, calves, and ankles",
                "Engages the core and stretches the shoulders"
            ),
            breathingCue = "Inhale to lift the chest; exhale to sit a little deeper.",
            modifications = "Practice with your back against a wall.",
            pregnancyAllowed = true,
            iconType = "standing"
        ),
        YogaPose(
            slug = "crescent_lunge_pose",
            name = "Forearm Stand",
            sanskritName = "Pincha Mayurasana",
            category = "Inversion",
            targets = listOf("Mental Focus", "Energy"),
            difficulty = "Advanced",
            durationSeconds = 45,
            instructions = listOf(
                "Come to your forearms with elbows shoulder-width apart.",
                "Walk your feet in towards your elbows, lifting your hips high.",
                "Kick one leg up, followed by the other, finding balance."
            ),
            benefits = listOf(
                "Builds tremendous shoulder and upper back strength",
                "Improves balance and focus"
            ),
            breathingCue = "Steady, even breaths. Inhale to push the floor away.",
            modifications = "Use a wall to catch your heels.",
            pregnancyAllowed = false,
            iconType = "inversion"
        ),
        YogaPose(
            slug = "crow_pose",
            name = "Easy Pose",
            sanskritName = "Sukhasana",
            category = "Seated",
            targets = listOf("Mental Focus", "Stress Relief"),
            difficulty = "Beginner",
            durationSeconds = 300,
            instructions = listOf(
                "Sit on the floor and cross your shins.",
                "Rest your hands on your knees with palms facing up or down.",
                "Lengthen your spine and relax your shoulders."
            ),
            benefits = listOf(
                "Promotes inner calm and tranquility",
                "Gently opens the hips and lengthens the spine"
            ),
            breathingCue = "Inhale to grow tall; exhale to ground through the sit bones.",
            modifications = "Sit on a cushion to elevate the hips.",
            pregnancyAllowed = true,
            iconType = "meditation"
        ),
        YogaPose(
            slug = "dancer_pose",
            name = "Camel Pose",
            sanskritName = "Ustrasana",
            category = "Backbend",
            targets = listOf("Energy", "Flexibility"),
            difficulty = "Intermediate",
            durationSeconds = 45,
            instructions = listOf(
                "Kneel on the mat with knees hip-width apart.",
                "Place hands on your lower back and gently lean back.",
                "If comfortable, reach down to grab your heels."
            ),
            benefits = listOf(
                "Opens the entire front body",
                "Improves posture and spinal mobility"
            ),
            breathingCue = "Inhale to lift the heart center; exhale to surrender into the backbend.",
            modifications = "Keep your hands on your lower back.",
            pregnancyAllowed = false,
            iconType = "backbend"
        ),
        YogaPose(
            slug = "downward_facing_dog_pose",
            name = "Handstand",
            sanskritName = "Adho Mukha Vrksasana",
            category = "Inversion",
            targets = listOf("Mental Focus", "Energy"),
            difficulty = "Advanced",
            durationSeconds = 30,
            instructions = listOf(
                "Start in downward dog facing a wall.",
                "Walk feet in slightly, keep arms straight, and kick one leg up.",
                "Engage core and squeeze legs together to find balance."
            ),
            benefits = listOf(
                "Strengthens shoulders, arms, and wrists",
                "Increases blood flow to the brain"
            ),
            breathingCue = "Breathe steadily; inhale to press the floor away.",
            modifications = "Practice 'L-shape' handstand against a wall.",
            pregnancyAllowed = false,
            iconType = "inversion"
        ),
        YogaPose(
            slug = "eagle_pose",
            name = "High Lunge",
            sanskritName = "Ashta Chandrasana",
            category = "Standing",
            targets = listOf("Energy", "Mental Focus"),
            difficulty = "Beginner",
            durationSeconds = 60,
            instructions = listOf(
                "Step one foot forward into a lunge.",
                "Keep the back heel lifted and the back leg completely straight.",
                "Inhale your arms up alongside your ears."
            ),
            benefits = listOf(
                "Strengthens the legs and core",
                "Stretches the chest and hip flexors"
            ),
            breathingCue = "Inhale to reach up; exhale to bend deeper into the front knee.",
            modifications = "Lower the back knee for a supported lunge.",
            pregnancyAllowed = true,
            iconType = "standing"
        ),
        YogaPose(
            slug = "eka_pada_koundinyasana_pose",
            name = "Dancer Pose",
            sanskritName = "Natarajasana",
            category = "Balancing",
            targets = listOf("Mental Focus", "Flexibility"),
            difficulty = "Intermediate",
            durationSeconds = 45,
            instructions = listOf(
                "Stand tall, shift weight onto your right leg, bend left knee to grab foot.",
                "Extend right arm forward and kick left foot into your hand.",
                "Hinge slightly forward at the hips."
            ),
            benefits = listOf(
                "Improves balance and focus",
                "Deeply stretches the shoulders and thighs"
            ),
            breathingCue = "Inhale to lift the chest; exhale to kick the foot harder.",
            modifications = "Hold onto a wall or chair for balance.",
            pregnancyAllowed = false,
            iconType = "balancing"
        ),
        YogaPose(
            slug = "extended_side_angle_pose",
            name = "Crow Pose",
            sanskritName = "Bakasana",
            category = "Balancing",
            targets = listOf("Mental Focus", "Energy"),
            difficulty = "Intermediate",
            durationSeconds = 30,
            instructions = listOf(
                "Squat down and place your hands flat on the mat.",
                "Bring your knees high up onto the backs of your upper arms.",
                "Shift weight forward into hands and lift feet off the floor."
            ),
            benefits = listOf(
                "Strengthens the arms, wrists, and core",
                "Develops confidence and body awareness"
            ),
            breathingCue = "Inhale to prepare; exhale to lift toes and squeeze the core.",
            modifications = "Place a block under your feet to get knees higher.",
            pregnancyAllowed = false,
            iconType = "balancing"
        ),
        YogaPose(
            slug = "extended_triangle_pose",
            name = "Standing Split",
            sanskritName = "Urdhva Prasarita Eka Padasana",
            category = "Balancing",
            targets = listOf("Flexibility", "Mental Focus"),
            difficulty = "Intermediate",
            durationSeconds = 45,
            instructions = listOf(
                "From a forward fold, shift weight onto your right leg.",
                "Lift left leg high toward the ceiling.",
                "Walk hands closer to your standing foot and draw torso in."
            ),
            benefits = listOf(
                "Intensely stretches hamstrings and calves",
                "Improves balance and memory"
            ),
            breathingCue = "Inhale to lengthen spine; exhale to lift the top leg higher.",
            modifications = "Keep hands on yoga blocks.",
            pregnancyAllowed = false,
            iconType = "balancing"
        ),
        YogaPose(
            slug = "feathered_peacock_pose",
            name = "Tree Pose",
            sanskritName = "Vrksasana",
            category = "Balancing",
            targets = listOf("Mental Focus", "Stress Relief"),
            difficulty = "Beginner",
            durationSeconds = 60,
            instructions = listOf(
                "Shift weight onto your left leg and bend right knee.",
                "Place sole of right foot on inner thigh or calf (avoid the knee).",
                "Bring hands to heart center or reach them up."
            ),
            benefits = listOf(
                "Improves physical and mental balance",
                "Strengthens the ankles and calves"
            ),
            breathingCue = "Inhale to feel grounded; exhale to soften shoulders.",
            modifications = "Keep the toes of the lifted foot resting on the floor.",
            pregnancyAllowed = true,
            iconType = "balancing"
        ),
        YogaPose(
            slug = "gate_pose",
            name = "Half Boat Pose",
            sanskritName = "Ardha Navasana",
            category = "Seated",
            targets = listOf("Energy", "Mental Focus"),
            difficulty = "Intermediate",
            durationSeconds = 45,
            instructions = listOf(
                "Lie on your back with your legs extended and arms reaching forward.",
                "Lift your head, shoulders, and legs off the mat, engaging your core.",
                "Keep your lower back pressing firmly into the floor."
            ),
            benefits = listOf(
                "Builds intense core and abdominal strength",
                "Improves endurance and focus"
            ),
            breathingCue = "Inhale to lengthen the body; exhale to hollow out the belly and lift.",
            modifications = "Bend your knees slightly if the lower back arches off the floor.",
            pregnancyAllowed = false,
            iconType = "seated"
        ),
        YogaPose(
            slug = "half_boat_pose",
            name = "Upward Salute",
            sanskritName = "Urdhva Hastasana",
            category = "Standing",
            targets = listOf("Energy", "Flexibility"),
            difficulty = "Beginner",
            durationSeconds = 60,
            instructions = listOf(
                "Stand with feet together and root down through the four corners of your feet.",
                "Inhale, sweep your arms out to the sides and up toward the ceiling.",
                "Gaze up at your thumbs and reach through your fingertips."
            ),
            benefits = listOf(
                "Stretches the belly and improves digestion",
                "Relieves stiffness in the shoulders and upper back"
            ),
            breathingCue = "Inhale to reach higher; exhale to ground down through the feet.",
            modifications = "Keep arms parallel if shoulders are tight rather than touching palms.",
            pregnancyAllowed = true,
            iconType = "standing"
        ),
        YogaPose(
            slug = "half_lord_of_the_fishes_pose",
            name = "High Lunge",
            sanskritName = "Ashta Chandrasana",
            category = "Standing",
            targets = listOf("Energy", "Mental Focus"),
            difficulty = "Intermediate",
            durationSeconds = 60,
            instructions = listOf(
                "From standing, step one foot back into a lunge.",
                "Keep the back heel lifted and the back leg straight.",
                "Inhale to lift the torso and extend your arms overhead."
            ),
            benefits = listOf(
                "Strengthens the legs, glutes, and core",
                "Stretches the hip flexors and chest"
            ),
            breathingCue = "Inhale to lift the chest; exhale to sink deeper into the front knee.",
            modifications = "Lower the back knee to the mat for extra stability.",
            pregnancyAllowed = true,
            iconType = "standing"
        ),
        YogaPose(
            slug = "handstand_pose",
            name = "Warrior I",
            sanskritName = "Virabhadrasana I",
            category = "Standing",
            targets = listOf("Energy", "Flexibility"),
            difficulty = "Beginner",
            durationSeconds = 60,
            instructions = listOf(
                "Step one foot forward into a lunge and spin the back heel flat at a 45-degree angle.",
                "Square your hips forward and deeply bend the front knee.",
                "Raise your arms straight up alongside your ears."
            ),
            benefits = listOf(
                "Strengthens the lower body and core",
                "Opens the chest and lungs"
            ),
            breathingCue = "Inhale to extend up through the fingertips; exhale to press the back heel down.",
            modifications = "Widen the stance laterally if balancing feels difficult.",
            pregnancyAllowed = true,
            iconType = "standing"
        ),
        YogaPose(
            slug = "handstand_split_pose",
            name = "Three-Legged Dog",
            sanskritName = "Eka Pada Adho Mukha Svanasana",
            category = "Inversion",
            targets = listOf("Energy", "Flexibility"),
            difficulty = "Intermediate",
            durationSeconds = 45,
            instructions = listOf(
                "From Downward-Facing Dog, ground firmly through both hands.",
                "Inhale and lift one leg high up and back.",
                "Keep your hips square to the mat and your lifted foot flexed."
            ),
            benefits = listOf(
                "Builds upper body strength",
                "Deepens the hamstring stretch of the standing leg"
            ),
            breathingCue = "Inhale to lift the leg higher; exhale to press the chest toward the thigh.",
            modifications = "Keep a micro-bend in the standing leg if the hamstring is tight.",
            pregnancyAllowed = false,
            iconType = "inversion"
        ),
        YogaPose(
            slug = "head_to_knee_forward_bend_pose",
            name = "Standing Bow Pose",
            sanskritName = "Dandayamana Dhanurasana",
            category = "Balancing",
            targets = listOf("Mental Focus", "Flexibility"),
            difficulty = "Advanced",
            durationSeconds = 45,
            instructions = listOf(
                "Stand tall, bend your right knee, and grasp the inside of your right ankle.",
                "Extend your left arm straight up.",
                "Kick your right foot back and up into your hand while hinging forward at the hips."
            ),
            benefits = listOf(
                "Improves balance and concentration",
                "Deeply opens the chest and shoulders"
            ),
            breathingCue = "Inhale to lift the chest; exhale to kick the foot deeper into the hand.",
            modifications = "Use a wall for balance with the extended hand.",
            pregnancyAllowed = false,
            iconType = "balancing"
        ),
        YogaPose(
            slug = "headstand_pose",
            name = "Mountain Pose (Prayer Hands)",
            sanskritName = "Tadasana",
            category = "Standing",
            targets = listOf("Mental Focus", "Stress Relief"),
            difficulty = "Beginner",
            durationSeconds = 60,
            instructions = listOf(
                "Stand with your big toes touching and heels slightly apart.",
                "Engage your thighs and tuck your tailbone slightly.",
                "Bring your palms together at your heart center and lengthen the spine."
            ),
            benefits = listOf(
                "Improves posture and body awareness",
                "Calms the mind and centers focus"
            ),
            breathingCue = "Inhale to stand taller; exhale to root down through the feet.",
            modifications = "Stand with feet hip-width apart for a wider base.",
            pregnancyAllowed = true,
            iconType = "standing"
        ),
        YogaPose(
            slug = "high_lunge_pose",
            name = "Revolved Downward-Facing Dog",
            sanskritName = "Parivrtta Adho Mukha Svanasana",
            category = "Twist",
            targets = listOf("Digestion", "Flexibility"),
            difficulty = "Intermediate",
            durationSeconds = 45,
            instructions = listOf(
                "From Downward Dog, reach your right hand back to grasp your left outer ankle or calf.",
                "Look under your left armpit, twisting your torso open.",
                "Keep the hips lifting high and weight distributed evenly in the feet."
            ),
            benefits = listOf(
                "Stretches the hamstrings and side body",
                "Massages internal organs to aid digestion"
            ),
            breathingCue = "Inhale to lengthen the spine; exhale to pull gently and deepen the twist.",
            modifications = "Hold onto the knee or thigh if reaching the ankle is difficult.",
            pregnancyAllowed = false,
            iconType = "twist"
        ),
        YogaPose(
            slug = "king_pigeon_pose",
            name = "Head-to-Knee Forward Bend",
            sanskritName = "Janu Sirsasana",
            category = "Forward Bend",
            targets = listOf("Stress Relief", "Flexibility"),
            difficulty = "Beginner",
            durationSeconds = 90,
            instructions = listOf(
                "Sit with one leg extended straight and the other bent, foot against the inner thigh.",
                "Inhale, reach your arms overhead to lengthen the spine.",
                "Exhale, fold forward over the extended leg, reaching for your foot."
            ),
            benefits = listOf(
                "Stretches the spine, hamstrings, and groins",
                "Calms the nervous system"
            ),
            breathingCue = "Inhale to find length through the chest; exhale to fold deeper.",
            modifications = "Use a strap around the extended foot if you cannot reach it.",
            pregnancyAllowed = true,
            iconType = "forward bend"
        ),
        YogaPose(
            slug = "knees_to_chest_pose",
            name = "Tree Pose (Prayer Overhead)",
            sanskritName = "Vrksasana",
            category = "Balancing",
            targets = listOf("Mental Focus", "Stress Relief"),
            difficulty = "Beginner",
            durationSeconds = 60,
            instructions = listOf(
                "Shift your weight onto one leg.",
                "Place the sole of the opposite foot on your inner calf or thigh.",
                "Bring hands to a prayer position and press them straight up toward the sky."
            ),
            benefits = listOf(
                "Strengthens the standing leg and ankle",
                "Improves balance and focus"
            ),
            breathingCue = "Inhale to reach hands up; exhale to ground down through the standing foot.",
            modifications = "Rest the lifted foot on the ankle like a kickstand.",
            pregnancyAllowed = true,
            iconType = "balancing"
        ),
        YogaPose(
            slug = "legs_up_the_wall_pose",
            name = "One-Legged Mountain Pose",
            sanskritName = "Eka Pada Tadasana",
            category = "Balancing",
            targets = listOf("Mental Focus", "Energy"),
            difficulty = "Beginner",
            durationSeconds = 45,
            instructions = listOf(
                "Stand tall in Mountain Pose.",
                "Shift your weight to one leg and lift the opposite knee to hip height.",
                "Extend your arms straight overhead and balance."
            ),
            benefits = listOf(
                "Builds core strength and stability",
                "Improves focus and prepares for deeper balances"
            ),
            breathingCue = "Inhale to reach tall; exhale to engage the core.",
            modifications = "Keep hands on hips for better balance.",
            pregnancyAllowed = true,
            iconType = "balancing"
        ),
        YogaPose(
            slug = "lord_of_the_dance_pose",
            name = "Happy Baby Pose",
            sanskritName = "Ananda Balasana",
            category = "Restorative",
            targets = listOf("Stress Relief", "Sleep"),
            difficulty = "Beginner",
            durationSeconds = 90,
            instructions = listOf(
                "Lie on your back and draw your knees into your chest.",
                "Grab the outside edges of your feet with your hands.",
                "Open your knees slightly wider than your torso and bring them toward your armpits."
            ),
            benefits = listOf(
                "Gently stretches the inner groins and spine",
                "Relieves tension in the lower back"
            ),
            breathingCue = "Breathe deeply into the belly, softening the lower back into the mat.",
            modifications = "Hold onto your shins or hamstrings if reaching the feet is straining.",
            pregnancyAllowed = true,
            iconType = "restorative"
        ),
        YogaPose(
            slug = "lotus_pose",
            name = "Standing Forward Bend",
            sanskritName = "Uttanasana",
            category = "Forward Bend",
            targets = listOf("Stress Relief", "Flexibility"),
            difficulty = "Beginner",
            durationSeconds = 60,
            instructions = listOf(
                "Stand tall, exhale, and hinge at the hips to fold forward.",
                "Let your head hang heavy and keep your neck relaxed.",
                "Bring your hands to the floor, blocks, or opposite elbows."
            ),
            benefits = listOf(
                "Stretches the hamstrings, calves, and hips",
                "Relieves stress and calms the brain"
            ),
            breathingCue = "Inhale to lift halfway; exhale to fold deeper.",
            modifications = "Keep a generous bend in your knees to protect the lower back.",
            pregnancyAllowed = true,
            iconType = "forward bend"
        ),
        YogaPose(
            slug = "low_lunge_pose",
            name = "Scorpion Pose",
            sanskritName = "Vrischikasana",
            category = "Inversion",
            targets = listOf("Mental Focus", "Energy"),
            difficulty = "Advanced",
            durationSeconds = 30,
            instructions = listOf(
                "Start in Forearm Stand (Pincha Mayurasana).",
                "Bend your knees and arch your back, reaching your toes toward the crown of your head.",
                "Lift your chest and gaze forward between your forearms."
            ),
            benefits = listOf(
                "Intensely strengthens the core and shoulders",
                "Deeply opens the chest and spine"
            ),
            breathingCue = "Inhale to lift the chest; exhale to bend the knees further.",
            modifications = "Practice against a wall to slowly build up spinal flexibility.",
            pregnancyAllowed = false,
            iconType = "inversion"
        ),
        YogaPose(
            slug = "low_lunge_twist_pose",
            name = "Tree Pose Variation",
            sanskritName = "Vrksasana",
            category = "Balancing",
            targets = listOf("Mental Focus", "Stress Relief"),
            difficulty = "Beginner",
            durationSeconds = 60,
            instructions = listOf(
                "Stand on one leg, placing the sole of the opposite foot on your inner thigh or calf.",
                "Extend your arms up toward the ceiling like branches.",
                "Find a focal point in front of you to hold your balance."
            ),
            benefits = listOf(
                "Enhances balance and coordination",
                "Strengthens the core and ankles"
            ),
            breathingCue = "Inhale to grow tall; exhale to soften your facial muscles.",
            modifications = "Use a wall for support with one hand.",
            pregnancyAllowed = true,
            iconType = "balancing"
        ),
        YogaPose(
            slug = "monkey_pose",
            name = "Raised Legs Pose",
            sanskritName = "Urdhva Prasarita Padasana",
            category = "Restorative",
            targets = listOf("Sleep", "Energy"),
            difficulty = "Beginner",
            durationSeconds = 90,
            instructions = listOf(
                "Lie on your back with arms resting by your sides.",
                "Engage your core and lift your legs straight up toward the ceiling.",
                "Keep the lower back pressing firmly into the mat."
            ),
            benefits = listOf(
                "Improves circulation and drains tension from the legs",
                "Slightly engages the core while resting the spine"
            ),
            breathingCue = "Breathe steadily, allowing gravity to ground the femurs into the hip sockets.",
            modifications = "Rest your legs against a wall (Legs-Up-the-Wall) for a fully restorative version.",
            pregnancyAllowed = true,
            iconType = "restorative"
        ),
        YogaPose(
            slug = "mountain_pose",
            name = "Wide-Legged Forward Bend",
            sanskritName = "Prasarita Padottanasana",
            category = "Forward Bend",
            targets = listOf("Stress Relief", "Flexibility"),
            difficulty = "Intermediate",
            durationSeconds = 60,
            instructions = listOf(
                "Step your feet wide apart, toes pointing slightly inward.",
                "Exhale and hinge forward at the hips, bringing hands to the mat.",
                "Draw the crown of your head toward the floor."
            ),
            benefits = listOf(
                "Stretches the inner and back legs",
                "Calms the mind and relieves mild backaches"
            ),
            breathingCue = "Inhale to lengthen the spine; exhale to fold deeper.",
            modifications = "Place a yoga block under your hands or head for support.",
            pregnancyAllowed = true,
            iconType = "forward bend"
        ),
        YogaPose(
            slug = "one_legged_downward_dog_pose",
            name = "King Pigeon Pose",
            sanskritName = "Eka Pada Rajakapotasana",
            category = "Backbend",
            targets = listOf("Flexibility", "Energy"),
            difficulty = "Advanced",
            durationSeconds = 45,
            instructions = listOf(
                "From Pigeon Pose, walk your hands back to lift your torso upright.",
                "Bend your back knee, reaching back to clasp your foot or ankle.",
                "Square your chest forward and look slightly up."
            ),
            benefits = listOf(
                "Deeply opens the hip flexors and chest",
                "Increases spinal mobility"
            ),
            breathingCue = "Inhale to lift the heart; exhale to soften into the hips.",
            modifications = "Use a yoga strap around the back foot if you cannot comfortably reach it.",
            pregnancyAllowed = false,
            iconType = "backbend"
        ),
        YogaPose(
            slug = "plow_pose",
            name = "Plow Pose",
            sanskritName = "Halasana",
            category = "Inversion",
            targets = listOf("Stress Relief", "Flexibility"),
            difficulty = "Intermediate",
            durationSeconds = 60,
            instructions = listOf(
                "Lie flat on your back with arms resting by your sides.",
                "Use your core to lift your legs and hips up and over your head.",
                "Rest your toes on the floor behind your head and keep your legs straight."
            ),
            benefits = listOf(
                "Stretches the shoulders and spine",
                "Stimulates the thyroid gland and abdominal organs"
            ),
            breathingCue = "Breathe deeply and slowly into the back body, keeping the throat soft.",
            modifications = "Support your lower back with your hands if your toes do not touch the floor.",
            pregnancyAllowed = false,
            iconType = "inversion"
        ),
        YogaPose(
            slug = "puppy_pose",
            name = "Puppy Pose",
            sanskritName = "Uttana Shishosana",
            category = "Restorative",
            targets = listOf("Stress Relief", "Flexibility"),
            difficulty = "Beginner",
            durationSeconds = 60,
            instructions = listOf(
                "Start on your hands and knees in a tabletop position.",
                "Keep your hips stacked directly above your knees while walking your hands forward.",
                "Melting your chest and forehead down toward the mat."
            ),
            benefits = listOf(
                "Open the shoulders, upper back, and chest",
                "Relieves tension from chronic stress"
            ),
            breathingCue = "Inhale to lengthen the spine; exhale to melt the heart space closer to the floor.",
            modifications = "Rest your forehead on a yoga block if the floor is too far away.",
            pregnancyAllowed = true,
            iconType = "restorative"
        ),
        YogaPose(
            slug = "raised_legs_pose",
            name = "Gate Pose",
            sanskritName = "Parighasana",
            category = "Stretching",
            targets = listOf("Flexibility", "Energy"),
            difficulty = "Beginner",
            durationSeconds = 45,
            instructions = listOf(
                "Kneel on your mat and extend your right leg straight out to the side.",
                "Inhale and raise your left arm overhead, then exhale to bend your torso toward the extended right leg.",
                "Rest your right hand down along your right leg."
            ),
            benefits = listOf(
                "Stretches the sides of the torso and the hamstrings",
                "Opens up the intercostal muscles between the ribs"
            ),
            breathingCue = "Inhale to grow tall through the fingertips; exhale to bend deeper into the side stretch.",
            modifications = "Place a folded blanket under the kneeling knee for extra cushioning.",
            pregnancyAllowed = true,
            iconType = "stretching"
        ),
        YogaPose(
            slug = "revolved_triangle_pose",
            name = "Revolved Triangle Pose",
            sanskritName = "Parivrtta Trikonasana",
            category = "Twist",
            targets = listOf("Digestion", "Flexibility"),
            difficulty = "Intermediate",
            durationSeconds = 45,
            instructions = listOf(
                "Step your feet wide apart, turning your front foot forward and back foot slightly out.",
                "Extend your arms, hinge forward, and place your opposite hand down inside or outside your front foot.",
                "Twist your torso and reach your other arm up toward the ceiling."
            ),
            benefits = listOf(
                "Improves spinal mobility and deep digestion",
                "Stretches the hips and hamstrings intensely"
            ),
            breathingCue = "Inhale to lengthen the spine; exhale to rotate the chest upward.",
            modifications = "Place your bottom hand on a yoga block instead of the floor.",
            pregnancyAllowed = false,
            iconType = "twist"
        ),
        YogaPose(
            slug = "scorpion_handstand_pose",
            name = "Seated Staff Pose",
            sanskritName = "Dandasana",
            category = "Seated",
            targets = listOf("Posture", "Mental Focus"),
            difficulty = "Beginner",
            durationSeconds = 60,
            instructions = listOf(
                "Sit tall on the floor with your legs extended straight in front of you.",
                "Flex your feet actively with toes pointing toward the ceiling.",
                "Place your palms flat on the mat beside your hips, lengthening your spine."
            ),
            benefits = listOf(
                "Strengthens the back muscles and improves posture",
                "Stretches the shoulders and hamstrings"
            ),
            breathingCue = "Inhale to lift through the crown of the head; exhale to root your sit bones down.",
            modifications = "Sit on a folded blanket if your lower back tends to round.",
            pregnancyAllowed = true,
            iconType = "seated"
        ),
        YogaPose(
            slug = "scorpion_pose",
            name = "Scorpion Pose",
            sanskritName = "Vrischikasana",
            category = "Inversion",
            targets = listOf("Energy", "Mental Focus"),
            difficulty = "Advanced",
            durationSeconds = 30,
            instructions = listOf(
                "Begin in a forearm stand with your elbows shoulder-width apart.",
                "Engage your core, bend your knees, and arch your back to bring your feet toward your head.",
                "Keep your gaze forward and balance steadily."
            ),
            benefits = listOf(
                "Builds exceptional strength in the upper body and shoulders",
                "Deeply enhances spinal flexibility"
            ),
            breathingCue = "Breathe steadily; inhale to lift the chest, exhale to maintain core stability.",
            modifications = "Practice with a partner or against a wall to catch your balance safely.",
            pregnancyAllowed = false,
            iconType = "inversion"
        ),
        YogaPose(
            slug = "seated_mountain_pose",
            name = "Seated Mountain Pose",
            sanskritName = "Tadasana Variant",
            category = "Seated",
            targets = listOf("Mental Focus", "Stress Relief"),
            difficulty = "Beginner",
            durationSeconds = 60,
            instructions = listOf(
                "Sit comfortably cross-legged or in an upright seated position.",
                "Rest your hands gently on your knees with palms facing upward.",
                "Lengthen your spine, drop your shoulders, and close your eyes."
            ),
            benefits = listOf(
                "Promotes inner calm and mental clarity",
                "Encourages tall, aligned posture"
            ),
            breathingCue = "Take deep, slow breaths, focusing entirely on the flow of air.",
            modifications = "Sit on a bolster or cushion to keep your hips elevated and comfortable.",
            pregnancyAllowed = true,
            iconType = "seated"
        ),
        YogaPose(
            slug = "seated_spinal_twist_pose",
            name = "Seated Spinal Twist",
            sanskritName = "Ardha Matsyendrasana",
            category = "Twist",
            targets = listOf("Digestion", "Flexibility"),
            difficulty = "Intermediate",
            durationSeconds = 60,
            instructions = listOf(
                "Sit tall with your legs extended out in front of you.",
                "Bend your right knee and cross your right foot over your left thigh, placing it flat on the floor.",
                "Inhale to lengthen your spine, then exhale to twist toward your right knee, using your left elbow as leverage."
            ),
            benefits = listOf(
                "Massages abdominal organs to aid digestion",
                "Relieves tension in the spine and shoulders"
            ),
            breathingCue = "Inhale to sit taller; exhale to deepen the twist gently.",
            modifications = "Keep the bottom leg extended straight out if bending it is uncomfortable.",
            pregnancyAllowed = false,
            iconType = "twist"
        ),
        YogaPose(
            slug = "side_plank_pose",
            name = "Side Plank Pose",
            sanskritName = "Vasisthasana",
            category = "Balancing",
            targets = listOf("Energy", "Mental Focus"),
            difficulty = "Intermediate",
            durationSeconds = 45,
            instructions = listOf(
                "Start in a standard high plank position.",
                "Shift your weight onto your right hand and the outer edge of your right foot, stacking your left foot on top.",
                "Extend your left arm straight up toward the ceiling and lift your hips high."
            ),
            benefits = listOf(
                "Strengthens the wrists, arms, shoulders, and core",
                "Improves overall body balance and concentration"
            ),
            breathingCue = "Inhale to reach your top hand higher; exhale to press the floor away firmly.",
            modifications = "Drop your bottom knee to the mat for a modified, supported variation.",
            pregnancyAllowed = false,
            iconType = "balancing"
        ),
        YogaPose(
            slug = "staff_pose",
            name = "Supported Bridge Pose",
            sanskritName = "Setu Bandha Sarvangasana Variation",
            category = "Restorative",
            targets = listOf("Stress Relief", "Sleep"),
            difficulty = "Beginner",
            durationSeconds = 120,
            instructions = listOf(
                "Lie on your back with your knees bent and feet flat on the floor hip-width apart.",
                "Press into your feet to lift your hips up.",
                "Slide a yoga block securely underneath your sacrum to support your lower back."
            ),
            benefits = listOf(
                "Calms the nervous system and relieves mild depression",
                "Stretches the chest, neck, and spine without muscle strain"
            ),
            breathingCue = "Breathe slowly and softly into your belly, letting the block fully support your weight.",
            modifications = "Adjust the height of the block based on your flexibility level.",
            pregnancyAllowed = true,
            iconType = "restorative"
        ),
        YogaPose(
            slug = "standing_backbend_pose",
            name = "Standing Backbend",
            sanskritName = "Anuvittasana",
            category = "Backbend",
            targets = listOf("Energy", "Flexibility"),
            difficulty = "Beginner",
            durationSeconds = 45,
            instructions = listOf(
                "Stand tall in Mountain Pose with feet hip-width apart.",
                "Place your hands on your lower back for support, fingers pointing down.",
                "Inhale to lift your chest upward and gently arch back, keeping your neck relaxed."
            ),
            benefits = listOf(
                "Energizes the body and counters slouched posture",
                "Opens the chest and shoulders"
            ),
            breathingCue = "Inhale to lift the heart center toward the sky; exhale to release any back tension.",
            modifications = "Keep your chin slightly tucked toward your chest if your neck feels strained.",
            pregnancyAllowed = false,
            iconType = "backbend"
        ),
        YogaPose(
            slug = "standing_balancing_dancer_pose",
            name = "Bridge Pose",
            sanskritName = "Setu Bandhasana",
            category = "Backbend",
            targets = listOf("Energy", "Flexibility"),
            difficulty = "Beginner",
            durationSeconds = 60,
            instructions = listOf(
                "Lie on your back with your knees bent and feet flat on the floor, hip-width apart.",
                "Press your arms and feet into the mat, then exhale to lift your hips toward the ceiling.",
                "Interlace your fingers underneath you and roll your shoulders back."
            ),
            benefits = listOf(
                "Strengthens the glutes, hamstrings, and core",
                "Stretches the chest, neck, and spine"
            ),
            breathingCue = "Inhale to lift the hips higher; exhale to ground your feet firmly.",
            modifications = "Place a block between your thighs to keep proper alignment.",
            pregnancyAllowed = true,
            iconType = "backbend"
        ),
        YogaPose(
            slug = "standing_dropback_pose",
            name = "Dancer Pose",
            sanskritName = "Natarajasana",
            category = "Balancing",
            targets = listOf("Mental Focus", "Flexibility"),
            difficulty = "Intermediate",
            durationSeconds = 45,
            instructions = listOf(
                "Stand tall, shift your weight onto your right foot, and bend your left knee to catch the inside of your left foot.",
                "Extend your right arm forward for balance.",
                "Kick your left foot up and back into your hand while hinging your torso forward."
            ),
            benefits = listOf(
                "Improves balance, focus, and core stability",
                "Deeply stretches the shoulders, chest, and thighs"
            ),
            breathingCue = "Inhale to lengthen forward; exhale to kick back and upward.",
            modifications = "Hold onto a wall or a stable chair with your free hand for extra balance.",
            pregnancyAllowed = false,
            iconType = "balancing"
        ),
        YogaPose(
            slug = "standing_hand_to_big_toe_pose",
            name = "High Plank Pose",
            sanskritName = "Phalakasana",
            category = "Balancing",
            targets = listOf("Energy", "Mental Focus"),
            difficulty = "Beginner",
            durationSeconds = 60,
            instructions = listOf(
                "Get on your hands and knees, then step your feet back to straighten your legs.",
                "Stack your heels over the balls of your feet and keep your body in a straight line from head to heels.",
                "Engage your core strongly and press the floor away through your palms."
            ),
            benefits = listOf(
                "Builds robust strength in the core, shoulders, and arms",
                "Improves total body alignment and stability"
            ),
            breathingCue = "Inhale to lengthen through the crown; exhale to draw your navel tightly to your spine.",
            modifications = "Lower your knees to the mat for a supported plank version.",
            pregnancyAllowed = false,
            iconType = "balancing"
        ),
        YogaPose(
            slug = "standing_split_pose",
            name = "Boat Pose",
            sanskritName = "Navasana",
            category = "Seated",
            targets = listOf("Energy", "Mental Focus"),
            difficulty = "Intermediate",
            durationSeconds = 45,
            instructions = listOf(
                "Sit on your mat with your knees bent and feet flat on the floor.",
                "Lean back slightly, keeping your spine straight, and lift your feet off the floor until your shins are parallel to the ground.",
                "Extend your arms straight forward alongside your legs."
            ),
            benefits = listOf(
                "Strengthens the core, hip flexors, and spine",
                "Improves overall digestion and balance"
            ),
            breathingCue = "Inhale to lift and open your chest; exhale to engage your core deeper.",
            modifications = "Keep your hands resting lightly on the backs of your thighs for added support.",
            pregnancyAllowed = false,
            iconType = "seated"
        ),
        YogaPose(
            slug = "standing_tree_pose",
            name = "Handstand",
            sanskritName = "Adho Mukha Vrksasana",
            category = "Inversion",
            targets = listOf("Energy", "Mental Focus"),
            difficulty = "Advanced",
            durationSeconds = 30,
            instructions = listOf(
                "Start in a downward-facing dog facing a wall.",
                "Step one foot in, keep your arms completely straight, and kick your legs up to find vertical balance against the wall.",
                "Squeeze your inner thighs together and engage your core fully."
            ),
            benefits = listOf(
                "Builds tremendous upper body and wrist strength",
                "Increases blood flow to the brain and sharpens focus"
            ),
            breathingCue = "Breathe steadily; inhale to press the floor away, exhale to hold your core tight.",
            modifications = "Practice L-shaped handstands with your heels resting on the wall first.",
            pregnancyAllowed = false,
            iconType = "inversion"
        ),
        YogaPose(
            slug = "tree_pose",
            name = "Tree Pose",
            sanskritName = "Vrksasana",
            category = "Balancing",
            targets = listOf("Mental Focus", "Stress Relief"),
            difficulty = "Beginner",
            durationSeconds = 60,
            instructions = listOf(
                "Stand tall in Mountain Pose and shift your weight onto your left foot.",
                "Place the sole of your right foot onto your inner left thigh or calf, avoiding the side of the knee joint.",
                "Bring your hands to your heart center or reach them overhead like branches."
            ),
            benefits = listOf(
                "Improves physical balance and mental concentration",
                "Strengthens the ankles, calves, and thighs"
            ),
            breathingCue = "Inhale to feel grounded through your standing foot; exhale to soften your shoulders.",
            modifications = "Keep the toes of your lifted foot touching the floor as a kickstand.",
            pregnancyAllowed = true,
            iconType = "balancing"
        ),
        YogaPose(
            slug = "triangle_pose",
            name = "Triangle Pose",
            sanskritName = "Utthita Trikonasana",
            category = "Standing",
            targets = listOf("Flexibility", "Energy"),
            difficulty = "Beginner",
            durationSeconds = 60,
            instructions = listOf(
                "Step your feet wide apart, turning your front foot out 90 degrees.",
                "Extend your arms parallel to the floor, reach forward, and hinge at your front hip.",
                "Bring your bottom hand to your shin, ankle, or floor, and reach your top hand toward the sky."
            ),
            benefits = listOf(
                "Stretches the hips, groins, hamstrings, and calves",
                "Opens the chest and shoulders while improving stability"
            ),
            breathingCue = "Inhale to lengthen your torso; exhale to open your chest toward the sky.",
            modifications = "Rest your bottom hand on a yoga block instead of reaching all the way to the floor.",
            pregnancyAllowed = true,
            iconType = "standing"
        ),
        YogaPose(
            slug = "upward_salute_pose",
            name = "Upward Salute",
            sanskritName = "Urdhva Hastasana",
            category = "Standing",
            targets = listOf("Energy", "Flexibility"),
            difficulty = "Beginner",
            durationSeconds = 60,
            instructions = listOf(
                "Stand straight with your feet together and root down through the soles.",
                "Inhale and sweep your arms out and up toward the ceiling.",
                "Bring your palms to touch gently and gaze up toward your thumbs."
            ),
            benefits = listOf(
                "Stretches the entire body and improves posture",
                "Enhances digestion and relieves mild anxiety"
            ),
            breathingCue = "Inhale deeply as you reach upward; exhale to ground your feet firmly into the mat.",
            modifications = "Keep your arms shoulder-width apart if touching your palms strains your shoulders.",
            pregnancyAllowed = true,
            iconType = "standing"
        ),
        YogaPose(
            slug = "warrior_i_pose",
            name = "Warrior I",
            sanskritName = "Virabhadrasana I",
            category = "Standing",
            targets = listOf("Energy", "Mental Focus"),
            difficulty = "Beginner",
            durationSeconds = 60,
            instructions = listOf(
                "Step one foot back and turn your back heel flat at a 45-degree angle.",
                "Bend your front knee deeply, keeping it stacked directly over your ankle.",
                "Inhale to lift your torso and extend your arms straight overhead."
            ),
            benefits = listOf(
                "Strengthens legs, shoulders, and arms",
                "Opens the chest, lungs, and hip flexors"
            ),
            breathingCue = "Inhale to reach tall through your fingertips; exhale to sink deeper into your front lunge.",
            modifications = "Widen your stance laterally if you find it difficult to keep your hips square.",
            pregnancyAllowed = true,
            iconType = "standing"
        ),
        YogaPose(
            slug = "warrior_ii_pose",
            name = "Warrior II",
            sanskritName = "Virabhadrasana II",
            category = "Standing",
            targets = listOf("Energy", "Flexibility"),
            difficulty = "Beginner",
            durationSeconds = 60,
            instructions = listOf(
                "Step your feet wide apart, turning your front foot out 90 degrees and back foot slightly in.",
                "Extend your arms out parallel to the floor, gazing over your front fingertips.",
                "Bend your front knee until it is stacked over your ankle."
            ),
            benefits = listOf(
                "Builds strength in the legs and core",
                "Opens the hips and chest while increasing stamina"
            ),
            breathingCue = "Inhale to expand across your chest; exhale to sink lower into your front knee.",
            modifications = "Shorten your stance if your front knee hurts or if you lack stability.",
            pregnancyAllowed = true,
            iconType = "standing"
        ),
        YogaPose(
            slug = "warrior_iii_pose",
            name = "Warrior III",
            sanskritName = "Virabhadrasana III",
            category = "Balancing",
            targets = listOf("Mental Focus", "Energy"),
            difficulty = "Intermediate",
            durationSeconds = 45,
            instructions = listOf(
                "Stand on your right leg and hinge your torso forward while lifting your left leg straight back.",
                "Extend your arms straight forward in line with your torso to form a T-shape.",
                "Keep your standing leg strong and your hips level facing the floor."
            ),
            benefits = listOf(
                "Strengthens the ankles, legs, shoulders, and core muscles",
                "Improves dynamic balance and concentration"
            ),
            breathingCue = "Inhale to lengthen from fingertips to heel; exhale to engage your core for stability.",
            modifications = "Bring your hands to your heart center in prayer instead of extending them forward.",
            pregnancyAllowed = false,
            iconType = "balancing"
        ),
        YogaPose(
            slug = "wheel_pose",
            name = "Wheel Pose",
            sanskritName = "Urdhva Dhanurasana",
            category = "Backbend",
            targets = listOf("Energy", "Flexibility"),
            difficulty = "Advanced",
            durationSeconds = 45,
            instructions = listOf(
                "Lie on your back with your knees bent and feet flat on the floor, close to your sit bones.",
                "Place your palms on the floor beside your ears with your fingers pointing toward your shoulders.",
                "Press into your hands and feet to lift your hips and chest completely off the mat."
            ),
            benefits = listOf(
                "Deeply strengthens the entire back body, arms, and legs",
                "Open the chest and expands lung capacity dramatically"
            ),
            breathingCue = "Breathe steadily and deeply into your chest space, avoiding pinching in the lower back.",
            modifications = "Practice bridge pose or use a yoga block between your thighs for proper alignment.",
            pregnancyAllowed = false,
            iconType = "backbend"
        ),
        YogaPose(
            slug = "wide_legged_forward_bend_pose",
            name = "Wide-Legged Forward Bend",
            sanskritName = "Prasarita Padottanasana",
            category = "Forward Bend",
            targets = listOf("Stress Relief", "Flexibility"),
            difficulty = "Beginner",
            durationSeconds = 60,
            instructions = listOf(
                "Step your feet wide apart, turning your toes slightly inward.",
                "Inhale to lengthen your spine, then exhale and hinge forward from your hips.",
                "Bring your hands down to the mat or blocks and let your head hang loose."
            ),
            benefits = listOf(
                "Deeply stretches the hamstrings, calves, and inner groins",
                "Calms the brain and relieves mild fatigue"
            ),
            breathingCue = "Inhale to find a flat back and length; exhale to fold deeper toward the floor.",
            modifications = "Keep a micro-bend in your knees if your hamstrings are tight.",
            pregnancyAllowed = true,
            iconType = "forward bend"
        )
    )

    val targetPlans = listOf(
        TargetPlan(
            id = "plan_digestion",
            target = "Digestion",
            title = "Digestive Harmony & Gut Flow",
            subtitle = "Gentle twists & abdominal compresses to stimulate gut motility",
            description = "A soothing, targeted sequence designed to relieve bloating, stimulate visceral blood flow, and activate digestive enzymes through gentle spinal twists and seated compressions.",
            level = "Beginner",
            estimatedMinutes = 12,
            poses = listOf(
                PlanPoseItem("childs-pose", 90),
                PlanPoseItem("cat-cow-stretch", 60),
                PlanPoseItem("seated-forward-bend", 90),
                PlanPoseItem("cobra-pose", 45),
                PlanPoseItem("supine-spinal-twist", 90, "Both sides"),
                PlanPoseItem("happy-baby-pose", 75),
                PlanPoseItem("corpse-pose", 90)
            ),
            highlights = listOf("Relieves abdominal bloating", "Stimulates visceral circulation", "Soothes enteric nervous system")
        ),
        TargetPlan(
            id = "plan_mental_focus",
            target = "Mental Focus",
            title = "Clarity & Single-Pointed Focus",
            subtitle = "Grounding balance postures and steady gazes for mental stillness",
            description = "Channel scattered thoughts into centered awareness. Features balance poses and deliberate alignment to anchor the mind in the present moment.",
            level = "All Levels",
            estimatedMinutes = 14,
            poses = listOf(
                PlanPoseItem("childs-pose", 60),
                PlanPoseItem("downward-facing-dog", 75),
                PlanPoseItem("warrior-two", 60, "Right side"),
                PlanPoseItem("warrior-two", 60, "Left side"),
                PlanPoseItem("triangle-pose", 60, "Both sides"),
                PlanPoseItem("tree-pose", 60, "Both sides"),
                PlanPoseItem("bridge-pose", 60),
                PlanPoseItem("corpse-pose", 120)
            ),
            highlights = listOf("Enhances cognitive clarity", "Sharpens steady gaze (Drishti)", "Grounds overactive thoughts")
        ),
        TargetPlan(
            id = "plan_sleep",
            target = "Sleep",
            title = "Deep Rest & Evening Surrender",
            subtitle = "Parasympathetic down-regulation for profound, restorative sleep",
            description = "Slow, grounding postures designed to lower heart rate, ease nocturnal restlessness, and prepare your nervous system for deep sleep.",
            level = "Beginner",
            estimatedMinutes = 15,
            poses = listOf(
                PlanPoseItem("childs-pose", 90),
                PlanPoseItem("puppy-pose", 75),
                PlanPoseItem("seated-forward-bend", 90),
                PlanPoseItem("pigeon-pose", 90, "Both sides"),
                PlanPoseItem("supine-spinal-twist", 90, "Both sides"),
                PlanPoseItem("happy-baby-pose", 75),
                PlanPoseItem("legs-up-the-wall", 120),
                PlanPoseItem("corpse-pose", 150)
            ),
            highlights = listOf("Activates sleep hormones", "Releases physical daytime fatigue", "Calms racing bedtime thoughts")
        ),
        TargetPlan(
            id = "plan_energy",
            target = "Energy",
            title = "Vitality Awakening & Flow",
            subtitle = "Invigorating standing postures and heart openers for natural energy",
            description = "Awaken the body with dynamic breath flow, spinal expansion, and active standing postures that boost circulation without nervous jitters.",
            level = "Intermediate",
            estimatedMinutes = 14,
            poses = listOf(
                PlanPoseItem("cat-cow-stretch", 60),
                PlanPoseItem("downward-facing-dog", 75),
                PlanPoseItem("warrior-two", 60, "Right side"),
                PlanPoseItem("warrior-two", 60, "Left side"),
                PlanPoseItem("triangle-pose", 60, "Both sides"),
                PlanPoseItem("cobra-pose", 60),
                PlanPoseItem("camel-pose", 45),
                PlanPoseItem("bridge-pose", 60),
                PlanPoseItem("corpse-pose", 90)
            ),
            highlights = listOf("Boosts daytime alertness", "Opens lungs for oxygen delivery", "Builds core & spinal strength")
        ),
        TargetPlan(
            id = "plan_stress_relief",
            target = "Stress Relief",
            title = "Tension Release & Calm Mind",
            subtitle = "Deep hip openers and passive folds to melt physical stress",
            description = "Dissolve tension held in the neck, shoulders, and hips with slow, generous holds that trigger nervous system ease and emotional release.",
            level = "All Levels",
            estimatedMinutes = 16,
            poses = listOf(
                PlanPoseItem("childs-pose", 90),
                PlanPoseItem("cat-cow-stretch", 60),
                PlanPoseItem("puppy-pose", 75),
                PlanPoseItem("pigeon-pose", 90, "Both sides"),
                PlanPoseItem("seated-forward-bend", 90),
                PlanPoseItem("supine-spinal-twist", 90, "Both sides"),
                PlanPoseItem("legs-up-the-wall", 120),
                PlanPoseItem("corpse-pose", 120)
            ),
            highlights = listOf("Lowers cortisol response", "Frees chronic shoulder & neck tight spots", "Restores inner emotional equilibrium")
        ),
        TargetPlan(
            id = "plan_flexibility",
            target = "Flexibility",
            title = "Full Body Mobility & Length",
            subtitle = "Comprehensive elongation for hamstrings, hips, and spine",
            description = "A thorough mobility plan focusing on muscle elongation, joint range of motion, and mindful breath extension.",
            level = "All Levels",
            estimatedMinutes = 15,
            poses = listOf(
                PlanPoseItem("downward-facing-dog", 75),
                PlanPoseItem("triangle-pose", 60, "Both sides"),
                PlanPoseItem("seated-forward-bend", 90),
                PlanPoseItem("pigeon-pose", 90, "Both sides"),
                PlanPoseItem("cobra-pose", 45),
                PlanPoseItem("camel-pose", 45),
                PlanPoseItem("puppy-pose", 75),
                PlanPoseItem("corpse-pose", 90)
            ),
            highlights = listOf("Increases hip & hamstring range", "Improves overall postural alignment", "Decompresses vertebral column")
        )
    )

    val initialFavorites = listOf(
        FavoritePose("user_default", "childs-pose"),
        FavoritePose("user_default", "tree-pose"),
        FavoritePose("user_default", "supine-spinal-twist")
    )

    val initialCompletions: List<PracticeCompletion>
        get() {
            val list = mutableListOf<PracticeCompletion>()
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val cal = Calendar.getInstance()

            // Yesterday
            cal.add(Calendar.DAY_OF_YEAR, -1)
            list.add(
                PracticeCompletion(
                    userId = "user_default",
                    completedDate = sdf.format(cal.time),
                    planTarget = "Stress Relief",
                    durationMinutes = 16,
                    timestamp = cal.timeInMillis,
                    notes = "Felt deeply relaxed in Pigeon pose."
                )
            )

            // 2 days ago
            cal.add(Calendar.DAY_OF_YEAR, -1)
            list.add(
                PracticeCompletion(
                    userId = "user_default",
                    completedDate = sdf.format(cal.time),
                    planTarget = "Sleep",
                    durationMinutes = 15,
                    timestamp = cal.timeInMillis,
                    notes = "Fell asleep peacefully right after."
                )
            )

            // 3 days ago
            cal.add(Calendar.DAY_OF_YEAR, -1)
            list.add(
                PracticeCompletion(
                    userId = "user_default",
                    completedDate = sdf.format(cal.time),
                    planTarget = "Flexibility",
                    durationMinutes = 15,
                    timestamp = cal.timeInMillis,
                    notes = "Hamstrings feeling looser."
                )
            )

            return list
        }
}
